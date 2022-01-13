package lecturer.services;

import com.sun.istack.Nullable;
import java.util.List;
import lecturer.entities.Course;
import lecturer.entities.Hours;
import lecturer.entities.Student;
import lecturer.exceptions.RetrieveInfoException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommunicationService {
    private final transient RestTemplate restTemplate;

    public CommunicationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Get course.
     *
     * @param courseId course id
     * @param text text
     * @return response entity
     */
    public ResponseEntity<Course> getCourse(String courseId, String text) {
        ResponseEntity<Course> forEntity = restTemplate.getForEntity("http://localhost:8082/courses/get?courseId="
                + courseId, Course.class);
        ifSuccess(forEntity, text);
        return forEntity;
    }

    /**
     * Choose ta.
     *
     * @param lecturerId lecturer id
     * @param courseId course id
     * @param studentlecturerId student id
     * @param hours hours
     * @param text text
     * @return response entity
     */
    public ResponseEntity<Boolean> choose(String lecturerId, String courseId,
                                          String studentlecturerId,
                                          float hours, String text) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", lecturerId);
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Boolean> exchange = restTemplate.exchange(
                "http://localhost:8082/courses/hireTa?courseId="
                        + courseId + "&studentId=" + studentlecturerId + "&hours="
                        + hours,
                HttpMethod.PUT, entity, Boolean.class);
        ifSuccess(exchange, text);
        return exchange;
    }

    /**
     * Find students ids.
     *
     * @param lecturerId lecturer id
     * @param courseId course id
     * @param strategy strategy
     * @param text text
     * @return response entity
     */
    public ResponseEntity<List<String>> studentIds(String lecturerId, String courseId,
                                                   String strategy, String text) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", lecturerId);
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<String>> exchange = restTemplate.exchange(
                "http://localhost:8082/courses"
                        + "/taRecommendations?courseId=" + courseId + "&strategy=" + strategy,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<String>>() {
                });
        ifSuccess(exchange, text);
        return exchange;
    }

    /**
     * Find list of students.
     *
     * @param sts list of ids
     * @param text text
     * @return response entity
     */
    public ResponseEntity<List<Student>> students(ResponseEntity<List<String>> sts, String text) {
        return restTemplate.exchange("http://localhost:8083/student/getMultiple",
                HttpMethod.POST, new HttpEntity<>((sts.getBody())),
                new ParameterizedTypeReference<List<Student>>() {});
    }

    /**
     * Approve hours.
     *
     * @param hours hours
     * @param text text
     * @return response entity
     */
    public ResponseEntity<Void> approveHours(List<Hours> hours, String text) {
        ResponseEntity<Void> exchange = restTemplate.exchange("http://localhost:8080/management/approveHours",
                HttpMethod.PUT, new HttpEntity<>(hours), Void.class);
        ifSuccess(exchange, text);
        return exchange;
    }

    /**
     * Disapprove hours.
     *
     * @param hours hours
     * @param text text
     * @return response entity
     */
    public ResponseEntity<Void> disapproveHours(List<Hours> hours, String text) {
        ResponseEntity<Void> exchange = restTemplate.exchange("http://localhost:8080/management/disapproveHours",
                HttpMethod.PUT, new HttpEntity<>(hours), Void.class);
        ifSuccess(exchange, text);
        return exchange;
    }

    /**
     * Average rating.
     *
     * @param studentId student id
     * @param text text
     * @return response entity
     */
    public ResponseEntity<Float> average(String studentId, String text) {
        ResponseEntity<Float> forEntity = restTemplate.getForEntity(
                "http://localhost:8080/management/getAverageRating?studentId=" + studentId,
                Float.class);
        ifSuccess(forEntity, text);
        return forEntity;
    }

    /**
     * Rate student.
     *
     * @param courseId course id
     * @param studentId student is
     * @param rating rating
     * @param text text
     * @return response entity
     */
    public ResponseEntity<Void> rate(String courseId, String studentId, float rating, String text) {
        ResponseEntity<Void> exchange = restTemplate.exchange("http://localhost:8080/management/rate?courseId="
                        + courseId + "&studentId=" + studentId + "&rating=" + rating,
                HttpMethod.PUT, null, Void.class);
        ifSuccess(exchange, text);
        return exchange;
    }

    /**
     * View student.
     *
     * @param studentId student id
     * @param text text
     * @return response entity
     */
    public ResponseEntity<Student> view(String studentId, String text) {
        ResponseEntity<Student> forEntity = restTemplate.getForEntity(
                "http://localhost:8083/student"
                        + "/get?id=" + studentId, Student.class);
        ifSuccess(forEntity, text);
        return forEntity;
    }

    /**
     * Check if request is valid.
     *
     * @param responseEntity response entity
     * @param text text
     */
    private void ifSuccess(@Nullable ResponseEntity<?> responseEntity, String text) {
        if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RetrieveInfoException(text);
        }
    }

}
