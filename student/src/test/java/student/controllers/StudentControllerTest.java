package student.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import student.entities.Management;
import student.entities.Student;
import student.services.CouplingService;
import student.services.StudentService;

public class StudentControllerTest {

    private transient Student student;
    private final transient String netId = "ohageman";
    private final transient String literallyNetId = "netId";
    private final transient String name = "Oisín";
    private final transient String courseId = "CSE1400-2021";
    private final transient String jsonStudent =
            "{\"netId\":\"ohageman\","
            + "\"name\": \"Oisín\","
            + "\"passedCourses\":{"
            + "\"CSE2115\":10.0,"
            + "\"CSE1400\":10.0,"
            + "\"CSE1105\":10.0"
            + "},"
            + "\"candidateCourses\":[\"CSE2115-2022\",\"CSE1400-2021\"],"
            + "\"taCourses\":[\"CSE1105-2022\"]"
            + "}";
    private final transient String jsonStudentList = "[" + jsonStudent + "]";
    private transient Map<String, Float> passedCourses;
    private transient Set<String> candidateCourses;
    private transient Set<String> taCourses;

    private transient MockMvc mockMvc;
    private transient StudentService studentService; // mocked
    private transient CouplingService couplingService; // mocked
    private transient StudentController studentController;

    @BeforeEach
    void setUp() {
        passedCourses = new HashMap<>();
        passedCourses.put("CSE2115", 10.0f);
        passedCourses.put("CSE1400", 10.0f);
        passedCourses.put("CSE1105", 10.0f);
        student = new Student(netId, name);
        candidateCourses = new HashSet<>();
        candidateCourses.add("CSE2115-2022");
        candidateCourses.add(courseId);
        taCourses = new HashSet<>();
        taCourses.add("CSE1105-2022");
        student.setPassedCourses(passedCourses);
        student.setCandidateCourses(candidateCourses);
        student.setTaCourses(taCourses);

        studentService = Mockito.mock(StudentService.class);
        couplingService = Mockito.mock(CouplingService.class);
        studentController = new StudentController(studentService, couplingService);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setMessageConverters(new GsonHttpMessageConverter())
                .build();
        Mockito.when(studentService.addStudent(student)).thenReturn(student);

    }

    @Test
    void getStudentTest() throws Exception {
        Mockito.when(studentService.getStudent(netId)).thenReturn(student);
        mockMvc.perform(get("/student/get?id=" + netId))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStudent));
    }

    @Test
    void getAllTest() throws Exception {
        Mockito.when(studentService.getAll()).thenReturn(List.of(student));
        mockMvc.perform(get("/student/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStudentList));
    }

    @Test
    void getMultipleTest() throws Exception {
        Mockito.when(studentService.getMultiple(List.of(netId))).thenReturn(List.of(student));
        List<Student> allStudents = new ArrayList<>();
        allStudents.add(student);
        mockMvc.perform(post("/student/getMultiple")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[\"ohageman\"]"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStudentList));
    }

    @Test
    void getPassedCoursesTest() throws Exception {
        Mockito.when(studentService.getPassedCourses(netId)).thenReturn(passedCourses);
        mockMvc.perform(get("/student/getPassedCourses")
                .header(literallyNetId, netId))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"CSE2115\":10.0,"
                        + "\"CSE1400\":10.0,"
                        + "\"CSE1105\":10.0"
                        + "}"));
    }

    @Test
    void getCandidateCoursesTest() throws Exception {
        Mockito.when(studentService.getCandidateCourses(netId)).thenReturn(candidateCourses);
        mockMvc.perform(get("/student/getCandidateCourses?id=" + netId))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"CSE2115-2022\",\"CSE1400-2021\"]"));
    }

    @Test
    void getTaCoursesTest() throws Exception {
        Mockito.when(studentService.getTaCourses(netId)).thenReturn(taCourses);
        mockMvc.perform(get("/student/getTACourses?id=" + netId))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"CSE1105-2022\"]"));
    }

    @Test
    void addStudentTest() throws Exception {
        Mockito.when(studentService.addStudent(student)).thenReturn(student);
        mockMvc.perform(post("/student/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStudent))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStudent));
    }

    @Test
    void applyTest() throws Exception {
        Mockito.when(couplingService.apply(netId, courseId)).thenReturn(student);
        mockMvc.perform(put("/student/apply?courseId=CSE1400-2021")
                .header(literallyNetId, netId))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStudent));
    }

    @Test
    void removeApplicationTest() throws Exception {
        Mockito.when(couplingService.removeApplication(netId, courseId)).thenReturn(student);
        mockMvc.perform(put("/student/removeApplication?courseId=CSE1400-2021")
                .header(literallyNetId, netId))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStudent));
    }

    @Test
    void acceptTest() throws Exception {
        Mockito.when(studentService.accept(netId, courseId)).thenReturn(student);
        mockMvc.perform(put("/student/accept?netId=" + netId
                + "&courseId=CSE1400-2021"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStudent));
    }

    @Test
    void declareHoursTest() throws Exception {
        mockMvc.perform(put("/student/declareHours")
                .header(literallyNetId, netId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("somejson"))
                .andExpect(status().isOk());
        Mockito.verify(couplingService).declareHours("somejson");
    }

    @Test
    void averageWorkedHoursTest() throws Exception {
        Mockito.when(couplingService.averageWorkedHours(courseId)).thenReturn(16.0f);
        mockMvc.perform(get("/student/averageWorkedHours?courseId=CSE1400-2021")
                .header(literallyNetId, netId))
                .andExpect(status().isOk())
                .andExpect(content().json("16.0"));
    }

    @Test
    void getManagementTest() throws Exception {
        String jsonManagement =
                "{\"courseId\":\"CSE1400\",\"studentId\":\"ohageman\",\"amountOfHours\":120.0}";
        Management management = new Management("CSE1400", netId, 120);
        Mockito.when(couplingService.getManagement(netId, "CSE1400")).thenReturn(management);
        mockMvc.perform(get("/student/getManagement?courseId=CSE1400")
                .header(literallyNetId, netId))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonManagement));
    }

}
