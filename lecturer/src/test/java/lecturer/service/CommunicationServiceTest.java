package lecturer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;
import java.util.List;
import lecturer.entities.Course;
import lecturer.entities.Hours;
import lecturer.entities.Student;
import lecturer.exceptions.RetrieveInfoException;
import lecturer.services.CommunicationService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class CommunicationServiceTest {
    @Mock
    private final transient RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    @InjectMocks
    private final transient CommunicationService communicationService
            = new CommunicationService(restTemplate);

    @Test
    public void getCourse() {
        ResponseEntity<Course> courseResponseEntity
                = new ResponseEntity<>(new Course(), HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(ArgumentMatchers.any(String.class),
                eq(Course.class)))
                .thenReturn(courseResponseEntity);
        assertEquals(courseResponseEntity, communicationService.getCourse("1", "text"));
    }

    @Test
    void getSpecificBadRequestCourse() {
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Course.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.getCourse("1", "CSE"));
    }

    @Test
    void getSpecificNullCourse() {
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Course.class)))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.getCourse("1", "CSE"));
    }

    @Test
    void chooseTa() {
        ResponseEntity<Boolean> responseEntity = new ResponseEntity<Boolean>(true, HttpStatus.OK);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.PUT), eq(entity), eq(Boolean.class)))
                .thenReturn(responseEntity);
        assertEquals(responseEntity, communicationService.choose("1", "CSE",
                "akalandadze", 20, "text"));
    }

    @Test
    void chooseTaBad() {
        ResponseEntity<Boolean> responseEntity = new ResponseEntity<Boolean>(true,
                HttpStatus.BAD_REQUEST);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.PUT), eq(entity), eq(Boolean.class)))
                .thenReturn(responseEntity);
        assertThrows(RetrieveInfoException.class, () -> communicationService.choose("1",
                "CSE", "akalandadze", 20, "text"));
    }

    @Test
    void chooseTaNull() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.PUT), eq(entity), eq(Boolean.class)))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class, () -> communicationService.choose("1",
                "CSE", "akalandadze", 20, "text"));
    }

    @Test
    void getStudentIds() {
        ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(List.of("1"),
                HttpStatus.OK);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET), eq(entity),
                eq((new ParameterizedTypeReference<List<String>>() {}))))
                .thenReturn(responseEntity);
        assertEquals(responseEntity, communicationService.studentIds("1", "CSE",
                "akalandadze", "text"));
    }

    @Test
    void getStudentIdsBad() {
        ResponseEntity<List<String>> responseEntity
                = new ResponseEntity<>(List.of("1"), HttpStatus.BAD_REQUEST);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET), eq(entity),
                eq((new ParameterizedTypeReference<List<String>>() {}))))
                .thenReturn(responseEntity);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.choose("1", "CSE",
                "akalandadze", 20, "text"));
    }

    @Test
    void getStudentIdsNull() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET), eq(entity),
                eq((new ParameterizedTypeReference<List<String>>() {}))))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.choose("1", "CSE",
                "akalandadze", 20, "text"));
    }

    @Test
    void getStudents() {
        ResponseEntity<List<String>> responseEntityInitial
                = new ResponseEntity<>(List.of("1"), HttpStatus.OK);
        ResponseEntity<List<Student>> responseEntity
                = new ResponseEntity<>(List.of(new Student()), HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.POST), eq(new HttpEntity<>(responseEntityInitial.getBody())),
                eq((new ParameterizedTypeReference<List<Student>>() {}))))
                .thenReturn(responseEntity);
        assertEquals(responseEntity,
                communicationService.students(responseEntityInitial, "text"));
    }

    @Test
    void getStudentsBad() {
        ResponseEntity<List<String>> responseEntityInitial
                = new ResponseEntity<>(List.of("1"), HttpStatus.OK);
        ResponseEntity<List<Student>> responseEntity
                = new ResponseEntity<>(List.of(new Student()), HttpStatus.BAD_REQUEST);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.POST), eq(new HttpEntity<>(responseEntityInitial.getBody())),
                        eq((new ParameterizedTypeReference<List<Student>>() {}))))
                .thenReturn(responseEntity);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.students(responseEntityInitial, "text"));
    }

    @Test
    void getStudentsNull() {
        ResponseEntity<List<String>> responseEntityInitial
                = new ResponseEntity<>(List.of("1"), HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.POST), eq(new HttpEntity<>(responseEntityInitial.getBody())),
                        eq((new ParameterizedTypeReference<List<String>>() {}))))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.students(responseEntityInitial, "text"));
    }

    @Test
    void approveNormal() {
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        List<Hours> hours = new ArrayList<>();
        hours.add(new Hours("CSE", "akalandadze", 10f));
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.PUT),
                eq(new HttpEntity<>(hours)), eq(Void.class)))
                .thenReturn(responseEntity);
        assertEquals(responseEntity, communicationService.approveHours(hours, "text"));
    }

    @Test
    void approveBadRequest() {
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<Hours> hours = new ArrayList<>();
        hours.add(new Hours("CSE", "akalandadze", 10f));
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.PUT),
                eq(new HttpEntity<>(hours)), eq(Void.class)))
                .thenReturn(responseEntity);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.approveHours(hours, "text"));
    }

    @Test
    void approveNull() {
        List<Hours> hours = new ArrayList<>();
        hours.add(new Hours("CSE", "akalandadze", 10f));
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.PUT),
                eq(new HttpEntity<>(hours)), eq(Void.class)))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.approveHours(hours, "text"));
    }

    @Test
    void disapproveNormal() {
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        List<Hours> hours = new ArrayList<>();
        hours.add(new Hours("CSE", "akalandadze", 10f));
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.PUT),
                eq(new HttpEntity<>(hours)), eq(Void.class)))
                .thenReturn(responseEntity);
        assertEquals(responseEntity, communicationService.disapproveHours(hours, "text"));
    }

    @Test
    void disapproveBadRequest() {
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<Hours> hours = new ArrayList<>();
        hours.add(new Hours("CSE", "akalandadze", 10f));
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.PUT),
                eq(new HttpEntity<>(hours)), eq(Void.class)))
                .thenReturn(responseEntity);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.disapproveHours(hours, "text"));
    }

    @Test
    void disapproveNull() {
        List<Hours> hours = new ArrayList<>();
        hours.add(new Hours("CSE", "akalandadze", 10f));
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.PUT),
                eq(new HttpEntity<>(hours)), eq(Void.class)))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.disapproveHours(hours, "text"));
    }

    @Test
    void averageNormal() {
        ResponseEntity<Float> responseEntity = new ResponseEntity<>(10f, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Float.class)))
                .thenReturn(responseEntity);
        assertEquals(responseEntity, communicationService.average("1", "text"));
    }

    @Test
    void averageBad() {
        ResponseEntity<Float> responseEntity = new ResponseEntity<>(10f, HttpStatus.BAD_REQUEST);
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Float.class)))
                .thenReturn(responseEntity);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.average("1", "text"));
    }

    @Test
    void averageNull() {
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Float.class)))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.average("1", "text"));
    }

    @Test
    void rateTa() {
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(restTemplate.exchange(any(String.class),
                eq(HttpMethod.PUT), eq(null), eq(Void.class)))
                .thenReturn(responseEntity);
        assertEquals(responseEntity,
                communicationService.rate("1", "CSE", 10f, "text"));
    }

    @Test
    void rateBadRequestTa() {
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Mockito.when(restTemplate.exchange(any(String.class),
                eq(HttpMethod.PUT), eq(null), eq(Void.class)))
                .thenReturn(responseEntity);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.rate("1", "akalandadze", 10f, "text"));
    }

    @Test
    void rateNull() {
        Mockito.when(restTemplate.exchange(any(String.class),
                eq(HttpMethod.PUT), eq(null), eq(Void.class)))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.rate("1", "akalandadze", 10f, "text"));
    }

    @Test
    void viewStudent() {
        ResponseEntity<Student> responseEntity
                = new ResponseEntity<>(new Student(), HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Student.class)))
                .thenReturn(responseEntity);
        assertEquals(responseEntity, communicationService.view("1",  "akalandadze"));
    }

    @Test
    void viewStudentBadRequest() {
        ResponseEntity<Student> responseEntity
                = new ResponseEntity<>(new Student(), HttpStatus.BAD_REQUEST);
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Student.class)))
                .thenReturn(responseEntity);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.view("1", "akalandadze"));
    }

    @Test
    void viewStudentNull() {
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Student.class)))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> communicationService.view("1", "akalandadze"));
    }
}
