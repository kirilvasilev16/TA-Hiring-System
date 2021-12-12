package lecturer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import lecturer.entities.Contract;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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

    public void verifyThatApplicableCourse(String netId, String courseId) {
        if (this.getOwnCourses(netId).contains(courseId)) {
            return;
        }
        throw new CourseNotFoundException("Course with id "
                + courseId + " is not taught by lecturer");
    }
    /**
     * Find specific course of a lecturer.
     *
     * @param netId netId of a lecturer
     * @param courseId specific course
     * @return course if lecturer is supervising it
     */
    public Course getSpecificCourseOfLecturer(String netId, String courseId) {
        this.verifyThatApplicableCourse(netId, courseId);
        ResponseEntity<Course> course = restTemplate.getForEntity("http://localhost:8082/courses/get?courseId=" + courseId, Course.class);
        if (course == null || course.getStatusCode()!=HttpStatus.OK) {
            throw new CourseNotFoundException("Course was not found.");
        }
        return course.getBody();
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
    public void chooseTa(String netId, String courseId, String studentNetId, int hours) {
        ObjectMapper objectMapper = new ObjectMapper();
        this.verifyThatApplicableCourse(netId, courseId);
        Contract contract = new Contract(netId, courseId, studentNetId, hours);
        try {
            ResponseEntity<Course> course = restTemplate.postForEntity("http://localhost:8082/courses/hireTA?courseId=" + courseId + "&studentId=" + studentNetId + "&hours=" + hours, objectMapper.writeValueAsString(contract), Course.class);
            if (course == null) {
                throw new CourseNotFoundException("Course was not found");
            }
            else if (course.getStatusCode()!=HttpStatus.OK) {
                throw new HttpClientErrorException(course.getStatusCode());
            }
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
    //todo
    public List<Student> getRecommendation(String netId, String courseId) {
        this.verifyThatApplicableCourse(netId, courseId);
        ResponseEntity<List<Student>> sts = restTemplate.exchange("http://localhost:8082/courses/taRecommendations/" + courseId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {});
        if (sts.getStatusCode()!=HttpStatus.OK) {
            throw new HttpClientErrorException(sts.getStatusCode());
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
        return course.getNumberOfTa();
    }
}
