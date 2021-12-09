package lecturer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lecturer.entities.Course;
import lecturer.entities.Lecturer;
import lecturer.entities.Student;
import lecturer.exceptions.CourseNotFoundException;
import lecturer.exceptions.LecturerNotFoundException;
import lecturer.repositories.LecturerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LecturerService {
    private final transient LecturerRepository lecturerRepository;
    private final transient RestTemplate restTemplate = new RestTemplate();

    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    /**
     * Find all lectures.
     *
     * @return all lectures
     */
    public List<Lecturer> findAll() {
        return lecturerRepository.findAll();
    }

    /**
     * Find lecturer by id.
     *
     * @param netId of a lecturer
     * @return lecturer with given netId
     */
    public Lecturer findLecturerById(String netId) {
        Optional<Lecturer> lecturer = lecturerRepository.findLecturerByNetId(netId);
        if (lecturer.isEmpty()) {
            throw new LecturerNotFoundException("Lecturer with id " + netId + " was not found.");
        }
        return lecturer.get();
    }

    /**
     * Find all courses of a lecturer.
     * Issue 15.
     *
     * @param netId of a lecturer
     * @return list of courses belonging to a lecturer
     */
    public List<Course> getOwnCourses(String netId) {
        return this.findLecturerById(netId).getCourses();
    }

    /**
     * Find specific course of a lecturer.
     *
     * @param netId netId of a lecturer
     * @param course specific course
     * @return course if lecturer is supervising it
     */
    public Course getSpecificCourseOfLecturer(String netId, Course course) {
        Lecturer lecturer = this.findLecturerById(netId);
        if (lecturer.getCourses().contains(course)) {
            return course;
        } else {
            throw new CourseNotFoundException("Course with id "
                    + course.getId() + " is not taught by lecturer");
        }
    }

    /**
     * Add lecturer.
     *
     * @param lecturer that should be added
     */
    public void addLecturer(Lecturer lecturer) {
        lecturerRepository.save(lecturer);
    }

    /**
     * Get list of candidates for a specific course.
     * Issue 17.
     *
     * @param netId of a lecturer
     * @param course specific course
     * @return list of students that want to be a TA for a course
     */
    public List<Student> getCandidateTaList(String netId, Course course) {
        return this.getSpecificCourseOfLecturer(netId, course).getCandidateTas();
    }

    /**
     * Choose TA for a course. I send post request to a course microservice to save changes there.
     * Issue 18.
     *
     * @param netId of a lecturer
     * @param course specific course
     * @param studentNetId netId of a student
     */
    public void chooseTa(String netId, Course course, String studentNetId) {
        ObjectMapper objectMapper = new ObjectMapper();
        Course neededCourse = this.getSpecificCourseOfLecturer(netId, course);
        try {
            restTemplate.postForEntity("http://localhost:8082/course/" + neededCourse.getId() + "/addTA/" + studentNetId, objectMapper.writeValueAsString(course), Course.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a course to a lecturer.
     * I expect here a request from course microservice with course as the request body.
     *
     * @param netId of a lecturer
     * @param courseId specific course id
     */
    public Lecturer addSpecificCourse(String netId, String courseId) {
        Lecturer lecturer = this.findLecturerById(netId);
        Course course = restTemplate.getForObject("http://localhost:8082/course/" + courseId, Course.class);
        lecturer.getCourses().add(course);
        lecturerRepository.save(lecturer);
        return lecturer;
    }

    /**
     * Gets average rating of a student.
     *
     * @param netId of a lecturer
     * @param course specific course
     * @param studentId id of a student
     * @return average rating of a student
     */
    public double computeAverageRating(String netId, Course course, String studentId) {
        List<Student> students = this.getCandidateTaList(netId, course);
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                return student.getAverageRating();
            }
        }
        return 0;
    }

    /**
     * Get recommendations of students for specific course.
     *
     * @param netId if of a lecturer
     * @param course specific course
     * @return list of recommended students
     */
    public List<Student> getRecommendation(String netId, Course course) {
        Course c = getSpecificCourseOfLecturer(netId, course);
        if (c == null) {
            return new ArrayList<>();
        }
        Student[] sts = restTemplate.getForObject("http://localhost:8082/course/recommendations" + course.getId(), Student[].class);
        if (sts == null) {
            return new ArrayList<>();
        }
        List<Student> targetList = new ArrayList<>();
        Collections.addAll(targetList, sts);
        return targetList;
    }

    /**
     * Get number of needed Ta's for course.
     *
     * @param netId id of a lecturer
     * @param course specific course
     * @return number of needed Ta's
     */
    public int getNumberOfNeededTas(String netId, Course course) {
        Course c = getSpecificCourseOfLecturer(netId, course);
        return c.getSize() / 20;
    }
}
