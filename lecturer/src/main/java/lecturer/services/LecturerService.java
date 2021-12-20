package lecturer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import lecturer.entities.Contract;
import lecturer.entities.Course;
import lecturer.entities.Lecturer;
import lecturer.entities.Management;
import lecturer.entities.Student;
import lecturer.exceptions.CourseNotFoundException;
import lecturer.exceptions.LecturerNotFoundException;
import lecturer.repositories.LecturerRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
     *
     * @param netId of a lecturer
     * @return list of courses belonging to a lecturer
     */
    public List<String> getOwnCourses(String netId) {
        return this.findLecturerById(netId).getCourses();
    }

    /**
     * Verify if course belongs to a lecturer.
     *
     * @param netId net id of lecturer
     * @param courseId course id
     */
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
        if (course == null || course.getStatusCode() != HttpStatus.OK) {
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
     *
     * @param netId of a lecturer
     * @param courseId specific course
     * @return list of students that want to be a TA for a course
     */
    public Set<String> getCandidateTaList(String netId, String courseId) {
        return this.getSpecificCourseOfLecturer(netId, courseId).getCandidateTas();
    }

    /**
     * Choose TA for a course. I send post request to a course microservice to save changes there.
     *
     * @param netId of a lecturer
     * @param courseId specific course
     * @param studentNetId netId of a student
     */
    public void chooseTa(String netId, String courseId, String studentNetId, float hours) {
        this.verifyThatApplicableCourse(netId, courseId);
        ResponseEntity<Course> course = restTemplate.exchange("http://localhost:8082/courses/hireTa?courseId=" + courseId + "&studentId=" + studentNetId + "&lecturerId=" + netId + "&hours=" + hours, HttpMethod.PUT, null, Course.class);
        if (course == null) {
            throw new CourseNotFoundException("Course was not found");
        } else if (course.getStatusCode() != HttpStatus.OK) {
            throw new HttpClientErrorException(course.getStatusCode());
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
        restTemplate.put("http://localhost:8082/courses/addLecturer?courseId=" + courseId + "&lecturerId=" + netId, null, Void.class);
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
        //        Set<String> students = this.getCandidateTaList(netId, course);
        //        for (String student : students) {
        //            if (student.getId().equals(studentId)) {
        //                return student.getAverageRating();
        //            }
        //        }
        throw new EntityNotFoundException();
    }

    /**
     * Get recommendations of students for specific course.
     *
     * @param netId if of a lecturer
     * @param courseId specific course
     * @return list of recommended students
     */
    public List<Student> getRecommendation(String netId, String courseId, int strategy) {
        this.verifyThatApplicableCourse(netId, courseId);
        ResponseEntity<List<String>> sts = restTemplate.exchange("http://localhost:8082/courses/taRecommendations?courseId=" + courseId + "&strategy=" + strategy, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
        if (sts == null) {
            throw new EntityNotFoundException();
        }
        if (sts.getStatusCode() != HttpStatus.OK) {
            throw new HttpClientErrorException(sts.getStatusCode());
        }
        ResponseEntity<List<Student>> stL = restTemplate.exchange("http://localhost:8083/student/getMultiple", HttpMethod.GET, new HttpEntity<>((sts.getBody())), new ParameterizedTypeReference<List<Student>>() {});
        if (stL.getStatusCode() != HttpStatus.OK) {
            throw new HttpClientErrorException(stL.getStatusCode());
        }
        return stL.getBody();
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
        return (int) Math.ceil(course.getCourseSize() / 20.0);
    }

    /**
     * Approve hours for a student.
     *
     * @param netId of a lecturer
     * @param contract includes courseId, studentId and hours
     */
    public void approveHours(String netId, Contract contract) {
        this.verifyThatApplicableCourse(netId, contract.getCourseId());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            restTemplate.postForEntity("http://localhost:8080/management/approveHours",
                    objectMapper.writeValueAsString(contract), Management.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
