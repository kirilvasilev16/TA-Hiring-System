package lecturer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;

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

        lecturer1 = new Lecturer("1", "name", "password", "email", courses);
        lecturer2 = new Lecturer("2", "name", "password", "email", new ArrayList<>());

        lecturers.add(lecturer1);
        lecturers.add(lecturer2);

        Mockito.when(lecturerRepository.findLecturerByNetId("1"))
                .thenReturn(java.util.Optional.ofNullable(lecturer1));
        Mockito.when(lecturerRepository.findLecturerByNetId("2"))
                .thenReturn(java.util.Optional.ofNullable(lecturer2));
        Mockito.when(lecturerRepository.findLecturerByNetId("3")).thenReturn(Optional.empty());
        Mockito.when(lecturerRepository.save(lecturer1)).thenReturn(lecturer1);
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
        Course courseEntity = new Course("CSE", new ArrayList<Student>());
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get/" + courseEntity.getId(), Course.class))
                .thenReturn(new ResponseEntity<>(courseEntity, HttpStatus.OK));
        Course c = lecturerService.getSpecificCourseOfLecturer("1", course);
        assertEquals("CSE", c.getId());
    }

    @Test
    void getSpecificNullCourse() {
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get/CSE", Course.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        assertThrows(CourseNotFoundException.class,
                () -> lecturerService.getSpecificCourseOfLecturer("1", "CSE"));
    }

    @Test
    void getSpecificNotContainedCourse() {
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get/11", Course.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertThrows(CourseNotFoundException.class,
                () -> lecturerService.getSpecificCourseOfLecturer("1", "11"));
    }

    @Test
    void addLecturer() {
        lecturerService.addLecturer(lecturer2);
        ArgumentCaptor<Lecturer> argument = ArgumentCaptor.forClass(Lecturer.class);
        Mockito.verify(lecturerRepository).save(argument.capture());
        assertEquals(lecturer2.getNetId(), argument.getValue().getNetId());
    }

    @Test
    void getTaCandidates() {
        Course courseEntity = new Course("CSE", new ArrayList<Student>());
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get/" + courseEntity.getId(), Course.class))
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
        List<Student> l = new ArrayList<Student>();
        l.add(new Student("1", 7.8));
        Course courseEntity = new Course("CSE", l);
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get/" + courseEntity.getId(), Course.class))
                .thenReturn(new ResponseEntity<>(courseEntity, HttpStatus.OK));
        assertEquals(7.8, lecturerService.computeAverageRating("1", "CSE", "1"));
    }

    @Test
    void computeNotCandidateRating() {
        List<Student> l = new ArrayList<Student>();
        l.add(new Student("1", 7.8));
        Course courseEntity = new Course("CSE", l);
        Mockito.when(restTemplate.getForEntity("http://localhost:8082/courses/get/" + courseEntity.getId(), Course.class))
                .thenReturn(new ResponseEntity<>(courseEntity, HttpStatus.OK));
        assertThrows(EntityNotFoundException.class, () -> lecturerService.computeAverageRating("1", "CSE", "2"));
    }
}
