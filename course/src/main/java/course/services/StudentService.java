package course.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import course.controllers.CourseController;
import course.controllers.strategies.ExperienceStrategy;
import course.controllers.strategies.GradeStrategy;
import course.controllers.strategies.RatingStrategy;
import course.controllers.strategies.TARecommendationStrategy;
import course.entities.Course;
import course.entities.Student;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.swing.plaf.IconUIResource;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentService {

    private static HttpClient client = HttpClient.newBuilder().build();
    private static Gson gson = new GsonBuilder()
            .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
    private static int ratingStrat = 1;
    private static int experienceStrat = 2;
    private static int gradeStrat = 3;

    /**
     * Getter for candidate TAs
     * @return Set of strings where strings are studentIDs
     */
    public static Set<String> getCandidates(Course course){
        return course.getCandidateTAs();
    }

    /**
     * Add set of candidate TAs, admin privilege
     * @param students Set of strings where strings are studentIDs
     */
    public static void addCandidateSet(Course course, Set<String> students){
        course.getCandidateTAs().addAll(students);
    }

    /**
     * Add student as candidate TA
     * @param studentID String studentID
     */
    public static void addCandidate(Course course, String studentID){
        if(containsTA(course, studentID)) return; //TODO: throw candidate already hired
        course.getCandidateTAs().add(studentID);
    }

    /**
     * Remove student from candidate list
     * @param studentID String studentID
     * @return true if removed, false otherwise
     */
    public static boolean removeCandidate(Course course, String studentID){
        if(!containsCandidate(course, studentID) || containsTA(course, studentID)) return false; //TODO: throw not candidate
        return course.getCandidateTAs().remove(studentID);
    }

    /**
     * Check if student is in the candidate list
     * @param studentID String studentID
     * @return true if student is candidate, false otherwise
     */
    public static boolean containsCandidate(Course course, String studentID){
        return course.getCandidateTAs().contains(studentID);
    }

    /**
     * Generate list of TA Recommendations
     * @param course String studentID
     * @param strategy 1 -> By rating, 2 -> By experience, 3 -> By course grade
     * @return list containing student ids in desired order
     */
    @SuppressWarnings("PMD")
    public static List<String> getTARecommendationList(Course course, Integer strategy){
        //Make request (POST)
        /*String idJson = gson.toJson(c.getCandidateTAs());

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(idJson))
                .uri(URI.create("http://localhost:8080/student/getstudents"))
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
        for(String sId : course.getCandidateTAs()){
            HttpRequest request = HttpRequest.newBuilder().GET()
                    .uri(URI.create("http://localhost:8080/student/get?id=" + sId))
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

        TARecommendationStrategy strategyImplementation;
        if(strategy == ratingStrat){
            strategyImplementation = new RatingStrategy(course);
        }else if(strategy == experienceStrat){
            strategyImplementation = new ExperienceStrategy();
        }else{
            strategyImplementation = new GradeStrategy(course);
        }

        return strategyImplementation.getRecommendedTAs(students);
    }

    /**
     * Hire candidate TA to be TA
     * @param studentID String studentID
     * @return true if hired, false otherwise
     */
    public static boolean hireTA(Course course, String studentID){

        if(removeCandidate(course, studentID)){
            course.getHiredTAs().add(studentID);
            //addTA(studentID);
            //TODO: access management microservice to create management
            return true;
        }else{
            if(containsTA(course, studentID)) return false; //TODO: throws student already hired
            else return false; //TODO: throw student not in course exception
        }
    }

    /**
     * Getter for hired TAs
     * @return Set of strings where strings are studentIDs
     */
    public static Set<String> getTASet(Course course){
        return course.getHiredTAs();
    }

    /**
     * Add set of students as TAs, admin privilege
     * @param students Set of Strings where strings are studentIDs
     */
    public static void addTASet(Course course, Set<String> students){
        course.getHiredTAs().addAll(students);
    }


    /**
     * Remove student from TA set
     * @param studentID String studentID
     * @return true if removed, false otherwise
     */
    public static boolean removeTA(Course course, String studentID){
        return course.getHiredTAs().remove(studentID);
    }

    /**
     * Check if student is a TA
     * @param studentID String studentID
     * @return true is student is a TA, false otherwise
     */
    public static boolean containsTA(Course course, String studentID){
        return course.getHiredTAs().contains(studentID);
    }

    //TODO: getAvgWorkedHOurs

    /**
     * Check if enough TA have been hired
     * @return true if enough, false otherwise
     */
    public static boolean enoughTAs(Course course){
        return course.getHiredTAs().size() >= course.getRequiredTAs();
    }

    public static int hiredTAs(Course course){
        return course.getHiredTAs().size();
    }
}
