package lecturer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import lecturer.entities.Course;
import lecturer.entities.Hours;
import lecturer.entities.Lecturer;
import lecturer.entities.Student;
import lecturer.exceptions.LecturerNotFoundException;
import lecturer.exceptions.OwnNoPermissionException;
import lecturer.exceptions.RetrieveInfoException;
import lecturer.repositories.LecturerRepository;
import lecturer.services.LecturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

/**
 * Here I suppress duplicates as tests are using similar objects.
 * While I could do duplicates as predefined strings,
 * I do not think this is a good idea for readability.
 */

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class LecturerServiceTest {
    @Mock
    private transient LecturerRepository lecturerRepository;
    @Mock
    private transient RestTemplate restTemplate;
    @InjectMocks
    private transient LecturerService lecturerService;

    private transient Lecturer lecturer1;
    private transient Lecturer lecturer2;
    private final transient List<String> courses = new ArrayList<>();
    private final transient String course = "CSE";
    private final transient ArrayList<Lecturer> lecturers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        courses.add(course);

        lecturerRepository = Mockito.mock(LecturerRepository.class);
        restTemplate = Mockito.mock(RestTemplate.class);

        lecturerService = new LecturerService(lecturerRepository, restTemplate);

        lecturer1 = new Lecturer("1", "name", "email", courses);
        lecturer2 = new Lecturer("2", "name", "email", new ArrayList<>());

        lecturers.add(lecturer1);
        lecturers.add(lecturer2);

        Mockito.when(lecturerRepository.findLecturerByLecturerId("1"))
                .thenReturn(java.util.Optional.ofNullable(lecturer1));
        Mockito.when(lecturerRepository.findLecturerByLecturerId("2"))
                .thenReturn(java.util.Optional.ofNullable(lecturer2));

        Mockito.when(lecturerRepository.save(lecturer1))
                .thenReturn(lecturer1);
    }

    @Test
    void findAll() {
        Mockito.when(lecturerRepository.findAll()).thenReturn(lecturers);
        assertEquals(lecturers, lecturerService.findAll());
    }

    @Test
    void findById() {
        assertEquals(lecturer1, lecturerService.findLecturerById("1"));
    }

    @Test
    void findByIdNullable() {
        Mockito.when(lecturerRepository.findLecturerByLecturerId("3")).thenReturn(Optional.empty());
        assertThrows(LecturerNotFoundException.class, () -> lecturerService.findLecturerById("3"));
    }

    @Test
    void getOwnCourses() {
        assertEquals(courses, lecturerService.getOwnCourses("1"));
    }

    @Test
    void verifyForLecturerException() {
        assertThrows(OwnNoPermissionException.class,
                () -> lecturerService.verifyThatApplicableCourse("1", "1"));
    }

    @Test
    void getSpecificCourse() {
        Course courseEntity = new Course("CSE", new HashSet<>(), 0);
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get?courseId=" + courseEntity.getCourseId(), Course.class))
                .thenReturn(new ResponseEntity<>(courseEntity, HttpStatus.OK));
        Course c = lecturerService.getSpecificCourseOfLecturer("1", course);
        assertEquals("CSE", c.getCourseId());
        assertEquals(0, c.getCandidateTas().size());
        assertEquals(0, c.getCourseSize());
        assertNull(c.getHiredTas());
    }

    @Test
    void getSpecificBadRequestCourse() {
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get?courseId=CSE", Course.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        assertThrows(RetrieveInfoException.class,
                () -> lecturerService.getSpecificCourseOfLecturer("1", "CSE"));
    }

    @Test
    void getSpecificNullCourse() {
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get?courseId=CSE", Course.class))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> lecturerService.getSpecificCourseOfLecturer("1", "CSE"));
    }

    @Test
    void getSpecificNotContainedCourse() {
        assertThrows(OwnNoPermissionException.class,
                () -> lecturerService.getSpecificCourseOfLecturer("1", "11"));
    }

    @Test
    void addLecturer() {
        lecturerService.addLecturer(lecturer2);
        ArgumentCaptor<Lecturer> argument = ArgumentCaptor.forClass(Lecturer.class);
        Mockito.verify(lecturerRepository).save(argument.capture());
        assertEquals(lecturer2.getLecturerId(), argument.getValue().getLecturerId());
    }

    @Test
    void getTaCandidates() {
        Course courseEntity = new Course("CSE", new HashSet<>(), 0);
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get?courseId=" + courseEntity.getCourseId(), Course.class))
                .thenReturn(new ResponseEntity<>(courseEntity, HttpStatus.OK));
        assertEquals(0, lecturerService.getCandidateTaList("1", course).size());
    }

    @Test
    void addCourse() {
        lecturerService.addSpecificCourse("2", course);
        ArgumentCaptor<Lecturer> argument = ArgumentCaptor.forClass(Lecturer.class);
        Mockito.verify(lecturerRepository).save(argument.capture());
        assertEquals(1, lecturer2.getCourses().size());
    }

    @Test
    void computeRating() {
        Set<String> candidateTas = new HashSet<>();
        candidateTas.add("akalandadze");
        Course course = new Course("CSE2215", candidateTas, 500);
        courses.add(course.getCourseId());
        lecturer1.setCourses(courses);
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Course.class)))
                .thenReturn(new ResponseEntity<Course>(course, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Float.class)))
                .thenReturn(new ResponseEntity<Float>(2.5f, HttpStatus.OK));
        assertEquals(2.5f, lecturerService.getAverage("1", "CSE2215", "akalandadze"));
    }

    @Test
    void computeRatingNull() {
        Set<String> candidateTas = new HashSet<>();
        candidateTas.add("akalandadze");
        Course course = new Course("CSE2215", candidateTas, 500);
        courses.add(course.getCourseId());
        lecturer1.setCourses(courses);
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Course.class)))
                .thenReturn(new ResponseEntity<Course>(course, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Float.class)))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> lecturerService.getAverage("1", "CSE2215", "akalandadze"));
    }

    @Test
    void chooseTa() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.PUT), eq(entity), eq(Boolean.class)))
                .thenReturn(new ResponseEntity<Boolean>(true, HttpStatus.OK));
        assertTrue(lecturerService.chooseTa("1", "CSE", "akalandadze", 20));
    }

    @Test
    void chooseTaNull() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.PUT), eq(entity), eq(Boolean.class))).thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> lecturerService.chooseTa("1", "CSE", "akalandadze", 20));
    }

    @Test
    void chooseTaBadRequest() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.PUT), eq(entity), eq(Boolean.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        assertThrows(RetrieveInfoException.class,
                () -> lecturerService.chooseTa("1", "CSE", "akalandadze", 20f));
    }

    @Test
    void computeNotCandidateRating() {
        Set<String> l = new HashSet<>();
        l.add(new String("1"));
        Course courseEntity = new Course("CSE", l, 0);
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get?courseId="
                + courseEntity.getCourseId(), Course.class))
                .thenReturn(new ResponseEntity<>(courseEntity, HttpStatus.OK));
        assertThrows(EntityNotFoundException.class,
                () -> lecturerService.getAverage("1", "CSE", "2"));
    }

    @Test
    void getRecommendation() {
        List<String> s = new ArrayList<>();
        s.add("akalandadze");
        List<Student> l = new ArrayList<Student>();
        l.add(new Student("akalandadze"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                eq(entity),
                eq(new ParameterizedTypeReference<List<String>>() {})))
                .thenReturn(new ResponseEntity<>(s, HttpStatus.OK));
        Mockito.when(restTemplate.exchange(any(String.class),
                eq(HttpMethod.POST),
                eq(new HttpEntity<>(s)),
                eq(new ParameterizedTypeReference<List<Student>>() {})))
                .thenReturn(new ResponseEntity<>(l, HttpStatus.OK));
        assertEquals(l, lecturerService.getRecommendation("1", "CSE", "ss"));
    }

    @Test
    void getNonExistingRecommendation() {
        assertThrows(OwnNoPermissionException.class,
                () -> lecturerService.getRecommendation("1", "4", "ss"));
    }

    @Test
    void invalidOneRequestRecommendation() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), eq(entity),
                eq(new ParameterizedTypeReference<List<String>>() {})))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        assertThrows(RetrieveInfoException.class,
                () -> lecturerService.getRecommendation("1", "CSE", "ss"));
    }

    @Test
    void nullOneRecommendation() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), eq(entity),
                eq(new ParameterizedTypeReference<List<String>>() {})))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> lecturerService.getRecommendation("1", "CSE", "ss"));
    }

    @Test
    void nullTwoRecommendation() {
        List<String> candidates = List.of("akalandadze");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), eq(entity),
                eq(new ParameterizedTypeReference<List<String>>() {})))
                .thenReturn(new ResponseEntity<>(candidates, HttpStatus.OK));
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST),
                eq(new HttpEntity<>(candidates)),
                eq(new ParameterizedTypeReference<List<Student>>() {})))
                .thenReturn(null);
        assertThrows(RetrieveInfoException.class,
                () -> lecturerService.getRecommendation("1", "CSE", "ss"));
    }

    @Test
    void badRequestTwoRecommendation() {
        List<String> candidates = List.of("akalandadze");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), eq(entity),
                eq(new ParameterizedTypeReference<List<String>>() {})))
                .thenReturn(new ResponseEntity<>(candidates, HttpStatus.OK));
        Mockito.when(restTemplate.exchange(any(String.class),
                eq(HttpMethod.POST), eq(new HttpEntity<>(candidates)),
                eq(new ParameterizedTypeReference<List<Student>>() {})))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        assertThrows(RetrieveInfoException.class,
                () -> lecturerService.getRecommendation("1", "CSE", "ss"));
    }

    @Test
    void getSize() {
        Course courseEntity = new Course("CSE", new HashSet<>(), 20);
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get?courseId=CSE",
                Course.class))
                .thenReturn(new ResponseEntity<>(courseEntity, HttpStatus.OK));
        assertEquals(1, lecturerService.getNumberOfNeededTas("1", "CSE"));
    }

    @Test
    void approveHoursExc() {
        assertThrows(EntityNotFoundException.class,
                () -> lecturerService.approveHours("1", new ArrayList<>()));
    }

    @Test
    void disapproveHoursExc() {
        assertThrows(EntityNotFoundException.class,
                () -> lecturerService.disapproveHours("1", new ArrayList<>()));
    }

    @Test
    void rateNotApplicableTa() {
        Course c  = new Course();
        c.setCourseId("CSE");
        c.setHiredTas(new HashSet<>());
        Mockito.when(restTemplate.getForEntity(any(String.class),
                eq(Course.class))).thenReturn(new ResponseEntity<>(c, HttpStatus.OK));
        assertThrows(OwnNoPermissionException.class,
                () -> lecturerService.rateTa("1", "CSE", "akalandadze", 10f));
    }

    @Test
    void rateTa() {
        Course c  = new Course();
        c.setCourseId("CSE");
        c.setHiredTas(Set.of("akalandadze"));
        Mockito.when(restTemplate.getForEntity(any(String.class),
                eq(Course.class))).thenReturn(new ResponseEntity<>(c, HttpStatus.OK));
        Mockito.when(restTemplate.exchange(any(String.class),
                eq(HttpMethod.PUT), eq(null), eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertTrue(lecturerService.rateTa("1", "CSE", "akalandadze", 10f));
    }

    @Test
    void rateBadRequestTa() {
        Course c  = new Course();
        c.setCourseId("CSE");
        c.setHiredTas(Set.of("akalandadze"));
        Mockito.when(restTemplate.getForEntity(any(String.class),
                eq(Course.class))).thenReturn(new ResponseEntity<>(c, HttpStatus.OK));
        Mockito.when(restTemplate.exchange(any(String.class),
                eq(HttpMethod.PUT), eq(null),
                eq(Void.class))).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        assertThrows(RetrieveInfoException.class,
                () -> lecturerService.rateTa("1", "CSE", "akalandadze", 10f));
    }

    @Test
    void viewStudentInHired() {
        Course c  = new Course();
        c.setCourseId("CSE");
        c.setHiredTas(Set.of("akalandadze"));
        Student st = new Student("akalandadze");
        Mockito.when(restTemplate.getForEntity(any(String.class),
                eq(Course.class))).thenReturn(new ResponseEntity<>(c, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Student.class)))
                .thenReturn(new ResponseEntity<>(st, HttpStatus.OK));
        assertEquals(st, lecturerService.viewStudent("1", "CSE", "akalandadze"));
    }

    @Test
    void viewStudentInCandidates() {
        Course c  = new Course();
        c.setCourseId("CSE");
        c.setHiredTas(Set.of());
        c.setCandidateTas(Set.of("akalandadze"));
        Student st = new Student("akalandadze");
        Mockito.when(restTemplate.getForEntity(any(String.class),
                eq(Course.class))).thenReturn(new ResponseEntity<>(c, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Student.class)))
                .thenReturn(new ResponseEntity<>(st, HttpStatus.OK));
        assertEquals(st, lecturerService.viewStudent("1", "CSE", "akalandadze"));
    }

    @Test
    void viewStudentBadRequest() {
        Course c  = new Course();
        c.setCourseId("CSE");
        c.setHiredTas(Set.of());
        c.setCandidateTas(Set.of("akalandadze"));
        Student st = new Student("akalandadze");
        Mockito.when(restTemplate.getForEntity(any(String.class),
                eq(Course.class))).thenReturn(new ResponseEntity<>(c, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Student.class)))
                .thenReturn(new ResponseEntity<>(st, HttpStatus.BAD_REQUEST));
        assertThrows(RetrieveInfoException.class,
                () -> lecturerService.viewStudent("1", "CSE", "akalandadze"));
    }

    @Test
    void viewNotContaining() {
        Course c  = new Course();
        c.setCourseId("CSE");
        c.setHiredTas(Set.of());
        c.setCandidateTas(Set.of());
        Mockito.when(restTemplate.getForEntity(any(String.class),
                eq(Course.class))).thenReturn(new ResponseEntity<>(c, HttpStatus.OK));
        assertThrows(OwnNoPermissionException.class,
                () -> lecturerService.viewStudent("1", "CSE", "akalandadze"));
    }

    @Test
    void approveNormal() {
        List<Hours> hours = new ArrayList<>();
        hours.add(new Hours("CSE", "akalandadze", 10f));
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.PUT),
                eq(new HttpEntity<>(hours)), eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertTrue(lecturerService.approveHours("1", hours));
    }

    @Test
    void approveBadRequest() {
        List<Hours> hours = new ArrayList<>();
        hours.add(new Hours("CSE", "akalandadze", 10f));
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.PUT),
                eq(new HttpEntity<>(hours)), eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        assertThrows(RetrieveInfoException.class, () -> lecturerService.approveHours("1", hours));
    }

    @Test
    void disapproveNormal() {
        List<Hours> hours = new ArrayList<>();
        hours.add(new Hours("CSE", "akalandadze", 10f));
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.PUT),
                eq(new HttpEntity<>(hours)), eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertTrue(lecturerService.disapproveHours("1", hours));
    }

    @Test
    void disapproveBadRequest() {
        List<Hours> hours = new ArrayList<>();
        hours.add(new Hours("CSE", "akalandadze", 10f));
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.PUT),
                eq(new HttpEntity<>(hours)), eq(Void.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        assertThrows(RetrieveInfoException.class,
                () -> lecturerService.disapproveHours("1", hours));
    }
}
