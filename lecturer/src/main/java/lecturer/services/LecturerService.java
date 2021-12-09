package lecturer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.sun.jdi.request.InvalidRequestStateException;
import lecturer.controllers.LecturerController;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lecturer.entities.Course;
import lecturer.entities.Lecturer;
import lecturer.entities.Student;
import lecturer.exceptions.CourseNotFoundException;
import lecturer.exceptions.LecturerNotFoundException;
import lecturer.repositories.LecturerRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class LecturerService {
    private final transient LecturerRepository lecturerRepository;
    private final transient RestTemplate restTemplate;

    public LecturerService(LecturerRepository lecturerRepository, RestTemplate restTemplate) {
        this.lecturerRepository = lecturerRepository;
        this.restTemplate = restTemplate;
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
    public List<String> getOwnCourses(String netId) {
        return this.findLecturerById(netId).getCourses();
    }

    /**
     * Find specific course of a lecturer.
     *
     * @param netId netId of a lecturer
     * @param courseId specific course
     * @return course if lecturer is supervising it
     */
    public Course getSpecificCourseOfLecturer(String netId, String courseId) {
        Lecturer lecturer = this.findLecturerById(netId);
        if (lecturer.getCourses().contains(courseId)) {
            ResponseEntity<Course> course = restTemplate.getForEntity("http://localhost:8082/courses/get/" + courseId, Course.class);
            if (course.getStatusCode()!=HttpStatus.OK) {
                throw new CourseNotFoundException("Course was not found.");
            }
            return course.getBody();
        } else {
            throw new CourseNotFoundException("Course with id "
                    + courseId + " is not taught by lecturer");
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
     * @param courseId specific course
     * @return list of students that want to be a TA for a course
     */
    public List<Student> getCandidateTaList(String netId, String courseId) {
        return this.getSpecificCourseOfLecturer(netId, courseId).getCandidateTas();
    }

    /**
     * Choose TA for a course. I send post request to a course microservice to save changes there.
     * Issue 18.
     *
     * @param netId of a lecturer
     * @param courseId specific course
     * @param studentNetId netId of a student
     */
    public void chooseTa(String netId, String courseId, String studentNetId) {
        ObjectMapper objectMapper = new ObjectMapper();
        Course neededCourse = this.getSpecificCourseOfLecturer(netId, courseId);
        try {
            restTemplate.postForEntity("http://localhost:8082/course/addTA/" + courseId + "/" + studentNetId, objectMapper.writeValueAsString(neededCourse), Course.class);
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
        lecturer.getCourses().add(courseId);
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
    public double computeAverageRating(String netId, String course, String studentId) {
        List<Student> students = this.getCandidateTaList(netId, course);
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                return student.getAverageRating();
            }
        }
        throw new EntityNotFoundException();
    }

    /**
     * Get recommendations of students for specific course.
     *
     * @param netId if of a lecturer
     * @param courseId specific course
     * @return list of recommended students
     */
    public List<Student> getRecommendation(String netId, String courseId) {
        Course c = getSpecificCourseOfLecturer(netId, courseId);
        if (c == null) {
            return new ArrayList<>();
        }
        ResponseEntity<List<Student>> sts = restTemplate.exchange("http://localhost:8082/course/recommendations" + courseId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {});
        if (sts.getStatusCode()!=HttpStatus.OK) {
            throw new InvalidRequestStateException();
        }
        return sts.getBody();
    }

    /**
     * Get number of needed Ta's for course.
     *
     * @param netId id of a lecturer
     * @param courseId specific course
     * @return number of needed Ta's
     */
    public int getNumberOfNeededTas(String netId, String courseId) {
        Course course = this.getSpecificCourseOfLecturer(netId, courseId);
        return course.getSize()/20;
    }
}
