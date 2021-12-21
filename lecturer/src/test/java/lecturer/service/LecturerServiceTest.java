package lecturer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    //    @Test
    //    void computeRating() {
    //        Set<String> l = new HashSet<>();
    //        l.add("1");
    //        Course courseEntity = new Course("CSE", l, 0);
    //        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get?courseId="
    //                + courseEntity.getCourseId(), Course.class))
    //                .thenReturn(new ResponseEntity<>(courseEntity, HttpStatus.OK));
    //        assertEquals(7.8, lecturerService.computeAverageRating("1", "CSE", "1"));
    //    }

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
        s.add("1");
        List<Student> l = new ArrayList<Student>();
        l.add(new Student("1", 7.8));
        Mockito.when(restTemplate.exchange(
                "http://localhost:8082/courses/taRecommendations?courseId=CSE&strategy=1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}))
                .thenReturn(new ResponseEntity<>(s, HttpStatus.OK));
        Mockito.when(restTemplate.exchange("http://localhost:8083/student/getMultiple",
                HttpMethod.GET,
                new HttpEntity<>(s),
                new ParameterizedTypeReference<List<Student>>() {}))
                .thenReturn(new ResponseEntity<List<Student>>(l, HttpStatus.OK));
        assertEquals(l, lecturerService.getRecommendation("1", "CSE", 1));
    }

    @Test
    void getNonExistingRecommendation() {
        List<String> s = new ArrayList<>();
        s.add("1");
        List<Student> l = new ArrayList<Student>();
        l.add(new Student("1", 7.8));
        Mockito.when(restTemplate.exchange("http://localhost:8082/courses/taRecommendations?courseId=CSE&strategy=1", HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {}))
                .thenReturn(new ResponseEntity<>(s, HttpStatus.OK));
        Mockito.when(restTemplate.exchange("http://localhost:8083/student/getMultiple", HttpMethod.GET, new HttpEntity<>(s), new ParameterizedTypeReference<List<Student>>() {}))
                .thenReturn(new ResponseEntity<List<Student>>(l, HttpStatus.OK));
        assertThrows(CourseNotFoundException.class,
                () -> lecturerService.getRecommendation("1", "4", 1));
    }

    @Test
    void invalidRequestRecommendation() {
        List<String> s = new ArrayList<>();
        s.add("1");
        List<Student> l = new ArrayList<Student>();
        l.add(new Student("1", 7.8));
        Mockito.when(restTemplate.exchange("http://localhost:8082/courses/taRecommendations?courseId=CSE&strategy=1", HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {}))
                .thenReturn(new ResponseEntity<>(s, HttpStatus.OK));
        Mockito.when(restTemplate.exchange("http://localhost:8083/student/getMultiple", HttpMethod.GET, new HttpEntity<>(s), new ParameterizedTypeReference<List<Student>>() {}))
                .thenReturn(new ResponseEntity<List<Student>>(l, HttpStatus.BAD_REQUEST));
        assertThrows(HttpClientErrorException.class,
                () -> lecturerService.getRecommendation("1", "CSE", 1));
    }

    @Test
    void getSize() {
        Course courseEntity = new Course("CSE", new HashSet<>(), 20);
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get?courseId=CSE", Course.class))
                .thenReturn(new ResponseEntity<>(courseEntity, HttpStatus.OK));
        assertEquals(1, lecturerService.getNumberOfNeededTas("1", "CSE"));
    }
}
