package lecturer.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lecturer.entities.Course;
import lecturer.entities.Lecturer;
import lecturer.services.LecturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("PMD")
@WebMvcTest
public class LecturerControllerTest {
    @Autowired
    private transient MockMvc mockMvc;
    @InjectMocks
    private transient LecturerController lecturerController;
    @MockBean
    private transient LecturerService lecturerService;

    private transient Lecturer lecturer1;
    private transient Lecturer lecturer2;
    private final transient List<String> courses = new ArrayList<>();
    private final transient ArrayList<Lecturer> lecturers = new ArrayList<>();
    private transient String findAll;
    private transient String findOne;

    @BeforeEach
    void setUp() {
        lecturerService = Mockito.mock(LecturerService.class);
        lecturerController = new LecturerController(lecturerService);
        courses.add("CSE2215");
        courses.add("CSE2315");
        lecturer1 = new Lecturer("1", "name", "email", courses);
        lecturer2 = new Lecturer("2", "name", "email", new ArrayList<>());
        lecturers.add(lecturer1);
        lecturers.add(lecturer2);
        findAll = "[{\"lecturerId\": \"1\",\"name\": \"name\","
                +
                "\"email\": \"email\",\"courses\": [\"CSE2215\", \"CSE2315\"]},"
                +
                " {\"netId\": \"2\",\n"
                +
                "                \"name\": \"name\",\n"
                +
                "                \"email\": \"email\",\n"
                +
                "                \"courses\": []}]";
        findOne = "{\"lecturerId\": \"2\",\n"
                +
                "                \"name\": \"name\",\n"
                +
                "                \"email\": \"email\",\n"
                +
                "                \"courses\": []}";

    }

    @Test
    void findAllTest() {
        when(lecturerService.findAll()).thenReturn(lecturers);
        assertEquals(lecturers, lecturerController.findAll());
    }

    @Test
    void getOne() {
        when(lecturerService.findLecturerById("2")).thenReturn(lecturer2);
        assertEquals(lecturer2, lecturerController.getLecturer("2"));
    }

    @Test
    void getOwnCourses() throws Exception {
        when(lecturerService.getOwnCourses("1")).thenReturn(courses);
        assertEquals(courses, lecturerController.getOwnCourses("1"));
    }

    @Test
    void getSpecificCourse() throws Exception {
        Course course = new Course("CSE2215", new HashSet<>(), 20);
        when(lecturerService.getSpecificCourseOfLecturer("1", "CSE2215")).thenReturn(course);
        assertEquals(course, lecturerController.getSpecificCourse("1", "CSE2215"));
    }

    @Test
    void getCandidates() {
        Set<String> l = new HashSet<>();
        when(lecturerService.getCandidateTaList("1", "CSE2215")).thenReturn(l);
        assertEquals(l, lecturerController.getCandidateTas("1", "CSE2215"));
    }

    @Test
    void addCourse() {
        when(lecturerService.addSpecificCourse("1", "CSE2215")).thenReturn(lecturer1);
        assertEquals(lecturer1, lecturerController.addSpecificCourse("1", "CSE2215"));
    }
}
