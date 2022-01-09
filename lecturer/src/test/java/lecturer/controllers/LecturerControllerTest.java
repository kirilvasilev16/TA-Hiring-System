package lecturer.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lecturer.entities.Course;
import lecturer.entities.Hours;
import lecturer.entities.Lecturer;
import lecturer.entities.Student;
import lecturer.services.LecturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Suppress is used here to avoid mistake "duplicate netId" and "duplicate CSE2215".
 * As netId is used quite often as a header as an actual
 * string, it doesn't make sense to out it in a variable. While I could do them as variables,
 * I do not think it would make a difference.
 * Here are tests, which can use duplicate information.
 */

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@WebMvcTest
public class LecturerControllerTest {
    @Autowired
    private transient MockMvc mockMvc;
    @Autowired
    private transient LecturerController lecturerController;
    @MockBean
    private transient LecturerService lecturerService;

    private transient Lecturer lecturer1;
    private transient Lecturer lecturer2;
    private final transient List<String> courses = new ArrayList<>();
    private final transient ArrayList<Lecturer> lecturers = new ArrayList<>();
    private final transient ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        courses.add("CSE2215");
        courses.add("CSE2315");
        lecturer1 = new Lecturer("1", "name", "email", courses);
        lecturer2 = new Lecturer("2", "name", "email", new ArrayList<>());
        lecturers.add(lecturer1);
        lecturers.add(lecturer2);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new LecturerController(lecturerService)).build();
    }

    @Test
    void findAllTest() throws Exception {
        when(lecturerService.findAll()).thenReturn(lecturers);
        this.mockMvc.perform(get("/lecturer/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(lecturers)));
    }

    @Test
    void getOne() throws Exception {
        when(lecturerService.findLecturerById("2")).thenReturn(lecturer2);
        this.mockMvc.perform(get("/lecturer/get")
                .header("netId", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(lecturer2)));
    }

    @Test
    void getOwnCourses() throws Exception {
        when(lecturerService.getOwnCourses("1")).thenReturn(courses);
        this.mockMvc.perform(get("/lecturer/courses/getOwnCourses")
                .header("netId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(courses)));
    }

    @Test
    void getSpecificCourse() throws Exception {
        Course course = new Course("CSE2215", new HashSet<>(), 20);
        when(lecturerService.getSpecificCourseOfLecturer("1", "CSE2215")).thenReturn(course);
        this.mockMvc.perform(get("/lecturer/courses/getSpecificCourse?courseId=CSE2215")
                .header("netId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(course)));
    }

    @Test
    void getCandidates() throws Exception {
        Set<String> l = new HashSet<>();
        when(lecturerService.getCandidateTaList("1", "CSE2215")).thenReturn(l);
        this.mockMvc.perform(get("/lecturer/courses/getCandidateTas?courseId=CSE2215")
                .header("netId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(l)));
    }

    @Test
    void addCourse() throws Exception {
        when(lecturerService.addSpecificCourse("1", "CSE2215")).thenReturn(lecturer1);
        this.mockMvc.perform(put("/lecturer/courses/addCourse?courseId=CSE2215")
                .header("netId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(lecturer1)));
    }

    @Test
    void selectTa() throws Exception {
        when(lecturerService.chooseTa("1", "CSE2215", "qw", 25)).thenReturn(true);
        this.mockMvc.perform(put(
                "/lecturer/courses/selectTa?courseId=CSE2215&studentId=qw&hours=25")
                .header("netId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAvg() throws Exception {
        when(lecturerService.getAverage("1", "CSE2215", "qw")).thenReturn(9f);
        this.mockMvc.perform(get("/lecturer/getAverageRating?courseId=CSE2215&studentId=qw")
                .header("netId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(9f)));
    }

    @Test
    void getRecommendations() throws Exception {
        Student student = new Student("2");
        List<Student> students = List.of(student);
        when(lecturerService.getRecommendation("1", "CSE2215", "qw")).thenReturn(students);
        this.mockMvc.perform(get("/lecturer/courses/recommendations?courseId=CSE2215&strategy=qw")
                .header("netId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(students)));
    }

    @Test
    void getNumber() throws Exception {
        when(lecturerService.getNumberOfNeededTas("1", "CSE2215")).thenReturn(10);
        this.mockMvc.perform(get("/lecturer/courses/getSize?courseId=CSE2215")
                .header("netId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void addLecturer() throws Exception {
        doNothing().when(lecturerService).addLecturer(lecturer1);
        this.mockMvc.perform(post("/lecturer/addLecturer")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lecturer1)))
                .andExpect(status().isOk());
    }

    @Test
    void rateTa() throws Exception {
        when(lecturerService.rateTa("1", "CSE2215", "st", 10f)).thenReturn(true);
        this.mockMvc.perform(get("/lecturer/rateTa?courseId=CSE2215&studentId=st&rating=10")
                .header("netId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void approveHours() throws Exception {
        List<Hours> hours = new ArrayList<>();
        hours.add(new Hours("1", "1", 1.0f));
        when(lecturerService.approveHours("1", hours)).thenReturn(true);
        this.mockMvc.perform(post("/lecturer/approveHours")
                .header("netId", "1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hours))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void disapproveHours() throws Exception {
        List<Hours> hours = new ArrayList<>();
        hours.add(new Hours("1", "1", 1.0f));
        when(lecturerService.disapproveHours("1", hours)).thenReturn(true);
        this.mockMvc.perform(post("/lecturer/disapproveHours")
                .header("netId", "1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hours))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void view() throws Exception {
        Student student = new Student("1");
        when(lecturerService.viewStudent("1", "1", "1"))
                .thenReturn(student);
        this.mockMvc.perform(get("/lecturer/view?courseId=1&studentId=1")
                .header("netId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(student)));
    }
}
