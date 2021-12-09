package course.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import course.controllers.CourseController;
import course.controllers.strategies.ExperienceStrategy;
import course.controllers.strategies.GradeStrategy;
import course.controllers.strategies.RatingStrategy;
import course.controllers.strategies.TaRecommendationStrategy;
import course.entities.Course;
import course.entities.Student;
import course.exceptions.CourseNotFoundException;
import course.exceptions.InvalidCandidateException;
import course.exceptions.InvalidHiringException;
import course.exceptions.InvalidStrategyException;
import course.exceptions.TooManyCoursesException;
import course.services.interfaces.CourseService;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudentService {

    private static final CommunicationService communicationService = new CommunicationService();
    private static HttpClient client = HttpClient.newBuilder().build();
    private static Gson gson = new GsonBuilder()
            .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
    private static int ratingStrat = 1;
    private static int experienceStrat = 2;
    private static int gradeStrat = 3;
    private static int maxCoursesPerQuarter = 3;

    /**
     * Getter for candidate TAs.
     *
     * @param course Course Object
     * @return Set of strings where strings are studentIDs
     */
    public static Set<String> getCandidates(Course course) {
        return course.getCandidateTas();
    }

    /**
     * Add set of candidate TAs, admin privilege.
     *
     * @param course   Course Object
     * @param students Set of strings where strings are studentIDs
     */
    public static void addCandidateSet(Course course, Set<String> students) {
        course.getCandidateTas().addAll(students);
    }

    /**
     * Add student as candidate TA.
     *
     * @param course    Course Object
     * @param studentId String studentId
     * @throws InvalidCandidateException if Student already hired as TA
     */
    public static void addCandidate(Course course, String studentId)
            throws InvalidCandidateException {
        if (containsTa(course, studentId)) {
            throw new InvalidCandidateException("Student already hired as TA");
        }
        course.getCandidateTas().add(studentId);
    }

    /**
     * Remove student from candidate list.
     *
     * @param course    Course Object
     * @param studentId String studentId
     * @return true if removed, false otherwise
     * @throws InvalidCandidateException if Student not a candidate TA
     */
    public static boolean removeCandidate(Course course, String studentId)
            throws InvalidCandidateException {
        if (!containsCandidate(course, studentId)) {
            throw new InvalidCandidateException("Student not a candidate TA");
        }
        return course.getCandidateTas().remove(studentId);
    }

    /**
     * Check if student is in the candidate list.
     *
     * @param course    Course Object
     * @param studentId String studentId
     * @return true if student is candidate, false otherwise
     */
    public static boolean containsCandidate(Course course, String studentId) {
        return course.getCandidateTas().contains(studentId);
    }

    /**
     * Generate list of TA Recommendations.

     * @param course String studentID
     * @param strategy Choose from "rating", "experience" or "grade"
     * @return list containing student ids in desired order
     */
    @SuppressWarnings("PMD")
    public static List<String> getTaRecommendationList(Course course, String strategy) {
        //Make request (POST)
        /*String idJson = gson.toJson(c.getCandidateTAs());

        HttpRequest request = HttpRequest.newBuilder()
        .POST(HttpRequest.BodyPublishers.ofString(idJson))
                .uri(URI.create("http://localhost:8083/student/getstudents"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }

        if (response.statusCode() != 200) {
            System.out.println("GET Status: " + response.statusCode());
        }
        System.out.println(response.body());
        Set<Student> students = gson.fromJson(response.body(), new TypeToken<Set<Student>>() {
        }.getType());*/

        Set<Student> students = new HashSet<>();
        for (String studentId : course.getCandidateTas()) {
            HttpRequest request = HttpRequest.newBuilder().GET()
                    .uri(URI.create("http://localhost:8083/student/get?id=" + studentId))
                    .build();
            HttpResponse<String> response;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                e.printStackTrace();
                return List.of();
            }

            if (response.statusCode() != CourseController.successCode) {
                System.out.println("GET Status: " + response.statusCode());
            }
            System.out.println(response.body());
            students.add(gson.fromJson(response.body(), Student.class));
        }

        TaRecommendationStrategy strategyImplementation;
        if (strategy.equals("rating")) {
            strategyImplementation = new RatingStrategy(course);
        } else if (strategy.equals("experience")) {
            strategyImplementation = new ExperienceStrategy();
        } else if (strategy.equals("grade")) {
            strategyImplementation = new GradeStrategy(course);
        } else {
            throw new InvalidStrategyException(strategy + " is not a valid strategy");
        }

        return strategyImplementation.getRecommendedTas(students);
    }

    /**
     * Hire candidate TA to be TA.
     *
     * @param course    Course Object
     * @param studentId String studentId
     * @param hours     float for contract hours
     * @return true if hired, false otherwise
     * @throws InvalidHiringException if student already hired or not in course
     */
    public static boolean hireTa(Course course, String studentId, float hours)
            throws InvalidHiringException {

        //TODO: who checks hours is valid?

        if (course.getCandidateTas().remove(studentId)) {
            course.getHiredTas().add(studentId);
            //TODO: save management object?
            communicationService.createManagement(course.getCourseId(), studentId, hours);
            return true;
        } else {
            if (containsTa(course, studentId)) {
                throw new InvalidHiringException("Student already hired");
            } else {
                throw new InvalidHiringException("Student not in course");
            }
        }
    }

    /**
     * Getter for hired TAs.
     *
     * @param course Course Object
     * @return Set of strings where strings are studentIDs
     */
    public static Set<String> getTaSet(Course course) {
        return course.getHiredTas();
    }


    /**
     * Add set of students as TAs, admin privilege.
     *
     * @param course   Course Object
     * @param students Set of Strings where strings are studentIDs
     */
    public static void addTaSet(Course course, Set<String> students) {
        course.getHiredTas().addAll(students);
    }


    /**
     * Remove student from TA set.
     *
     * @param course    Course Object
     * @param studentId String studentId
     * @return true if removed, false otherwise
     */
    public static boolean removeTa(Course course, String studentId) {
        return course.getHiredTas().remove(studentId);
    }

    /**
     * Check if student is a TA.
     *
     * @param course    Course Object
     * @param studentId String studentId
     * @return true is student is a TA, false otherwise
     */
    public static boolean containsTa(Course course, String studentId) {
        return course.getHiredTas().contains(studentId);
    }

    //TODO: getAvgWorkedHOurs

    /**
     * Check if enough TA have been hired.
     *
     * @param course Course Object
     * @return true if enough, false otherwise
     */
    public static boolean enoughTas(Course course) {
        return course.getHiredTas().size() >= course.getRequiredTas();
    }

    /**
     * Get number of TAs hired.
     *
     * @param course Course object
     * @return int
     */
    public static int hiredTas(Course course) {
        return course.getHiredTas().size();
    }

    /**
     * Checks to see the number of courses that the student is a TA/prospective TA for
     * does not exceed 3 per quarter.
     *
     * @param courseService  the course service
     * @param courseId       the course id
     * @param studentCourses the student courses
     */
    @SuppressWarnings("PMD")
    public static void checkQuarterCapacity(CourseService courseService,
                                            String courseId, Set<String> studentCourses) {
        Map<String, Integer> coursesPerQuarter = new HashMap<>();
        for (String id : studentCourses) {
            Course current = courseService.findByCourseId(id);
            if (current == null) {
                throw new CourseNotFoundException(courseId);
            }

            StringBuilder builder = new StringBuilder();

            String year = current.getCourseId().split("-")[1];
            builder.append(year);
            builder.append("-");
            builder.append(current.getQuarter());
            String yearQuarter = builder.toString();

            if (coursesPerQuarter.get(yearQuarter) == null) {
                coursesPerQuarter.put(yearQuarter, 0);
            }
            coursesPerQuarter.put(yearQuarter, coursesPerQuarter.get(yearQuarter) + 1);

            if (coursesPerQuarter.get(yearQuarter) > maxCoursesPerQuarter) {
                throw new TooManyCoursesException("Quarter "
                        + current.getQuarter() + " has too many courses");
            }

        }
    }
}
