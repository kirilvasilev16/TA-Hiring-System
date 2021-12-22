package lecturer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import lecturer.entities.Lecturer;
import lecturer.entities.Student;
import lecturer.exceptions.CourseNotFoundException;
import lecturer.exceptions.LecturerNotFoundException;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("PMD")
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
        Mockito.when(lecturerRepository.findLecturerByLecturerId("3")).thenReturn(Optional.empty());

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
        assertThrows(LecturerNotFoundException.class, () -> lecturerService.findLecturerById("3"));
    }

    @Test
    void getOwnCourses() {
        assertEquals(courses, lecturerService.getOwnCourses("1"));
    }

    @Test
    void getSpecificCourse() {
        Course courseEntity = new Course("CSE", new HashSet<>(), 0);
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get?courseId=" + courseEntity.getCourseId(), Course.class))
                .thenReturn(new ResponseEntity<>(courseEntity, HttpStatus.OK));
        Course c = lecturerService.getSpecificCourseOfLecturer("1", course);
        assertEquals("CSE", c.getCourseId());
    }

    @Test
    void getSpecificNullCourse() {
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get?courseId=CSE", Course.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        assertThrows(CourseNotFoundException.class,
                () -> lecturerService.getSpecificCourseOfLecturer("1", "CSE"));
    }

    @Test
    void getSpecificNotContainedCourse() {
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get?courseId=11", Course.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertThrows(CourseNotFoundException.class,
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
        assertEquals(0, lecturerService.getAverage("1", "CSE2215", "akalandadze"));
    }

    @Test
    void chooseTa() {
        Set<String> candidateTas = new HashSet<>();
        candidateTas.add("akalandadze");
        Course course = new Course("CSE2215", candidateTas, 500);
        courses.add(course.getCourseId());
        lecturer1.setCourses(courses);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.PUT), eq(entity), eq(Boolean.class)))
                .thenReturn(new ResponseEntity<Boolean>(true, HttpStatus.OK));
        assertTrue(lecturerService.chooseTa("1", "CSE2215", "akalandadze", 20));
    }

    @Test
    void chooseTaNull() {
        Set<String> candidateTas = new HashSet<>();
        candidateTas.add("akalandadze");
        Course course = new Course("CSE2215", candidateTas, 500);
        courses.add(course.getCourseId());
        lecturer1.setCourses(courses);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.PUT), eq(entity), eq(Boolean.class))).thenReturn(null);
        assertThrows(CourseNotFoundException.class,
                () -> lecturerService.chooseTa("1", "CSE2215", "akalandadze", 20));
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
        Set<String> candidateTas = new HashSet<>();
        candidateTas.add("akalandadze");
        Course course = new Course("CSE2215", candidateTas, 500);
        courses.add(course.getCourseId());
        lecturer1.setCourses(courses);
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
        Mockito.when(restTemplate.exchange("http://localhost:8083/student/getMultiple",
                HttpMethod.GET,
                new HttpEntity<>(s),
                new ParameterizedTypeReference<List<Student>>() {}))
                .thenReturn(new ResponseEntity<List<Student>>(l, HttpStatus.OK));
        assertEquals(l, lecturerService.getRecommendation("1", "CSE", "ss"));
    }

    @Test
    void getNonExistingRecommendation() {
        Set<String> candidateTas = new HashSet<>();
        candidateTas.add("akalandadze");
        Course course = new Course("CSE2215", candidateTas, 500);
        courses.add(course.getCourseId());
        lecturer1.setCourses(courses);
        List<String> s = new ArrayList<>();
        s.add("1");
        List<Student> l = new ArrayList<Student>();
        l.add(new Student("1"));
        Mockito.when(restTemplate.exchange("http://localhost:8082/courses/taRecommendations?courseId=CSE&strategy=rating", HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {}))
                .thenReturn(new ResponseEntity<>(s, HttpStatus.OK));
        Mockito.when(restTemplate.exchange("http://localhost:8083/student/getMultiple", HttpMethod.GET, new HttpEntity<>(s), new ParameterizedTypeReference<List<Student>>() {}))
                .thenReturn(new ResponseEntity<List<Student>>(l, HttpStatus.OK));
        assertThrows(CourseNotFoundException.class,
                () -> lecturerService.getRecommendation("1", "4", "ss"));
    }

    @Test
    void invalidRequestRecommendation() {
        Set<String> candidateTas = new HashSet<>();
        candidateTas.add("akalandadze");
        Course course = new Course("CSE2215", candidateTas, 500);
        courses.add(course.getCourseId());
        lecturer1.setCourses(courses);
        List<String> s = new ArrayList<>();
        s.add("akalandadze");
        List<Student> l = new ArrayList<Student>();
        l.add(new Student("akalandadze"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("netId", "1");
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        Mockito.when(restTemplate.getForEntity(any(String.class), eq(Course.class)))
                .thenReturn(new ResponseEntity<Course>(course, HttpStatus.OK));
        Mockito.when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), eq(entity),
                eq(new ParameterizedTypeReference<List<String>>() {})))
                .thenReturn(new ResponseEntity<>(s, HttpStatus.OK));
        Mockito.when(restTemplate.exchange("http://localhost:8083/student/getMultiple",
                HttpMethod.GET,
                new HttpEntity<>(s), new ParameterizedTypeReference<List<Student>>() {}))
                .thenReturn(new ResponseEntity<List<Student>>(l, HttpStatus.BAD_REQUEST));
        assertThrows(HttpClientErrorException.class,
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
}
