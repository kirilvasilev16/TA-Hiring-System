package lecturer.controllers;

import lecturer.entities.Course;
import lecturer.entities.Lecturer;
import lecturer.services.LecturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private transient String findAll;
    private transient String findOne;

    @BeforeEach
    void setUp () {
        courses.add("CSE2215");
        courses.add("CSE2315");
        lecturer1 = new Lecturer("1", "name", "password", "email", courses);
        lecturer2 = new Lecturer("2", "name", "password", "email", new ArrayList<>());
        lecturers.add(lecturer1);
        lecturers.add(lecturer2);
        findAll = "[{\"netId\": \"1\",\"name\": \"name\", \"password\": \"password\", \"email\": \"email\",\"courses\": [\"CSE2215\", \"CSE2315\"]}," +
                " {\"netId\": \"2\",\n" +
                "                \"name\": \"name\",\n" +
                "                \"password\": \"password\",\n" +
                "                \"email\": \"email\",\n" +
                "                \"courses\": []}]";
        findOne = "{\"netId\": \"2\",\n" +
                "                \"name\": \"name\",\n" +
                "                \"password\": \"password\",\n" +
                "                \"email\": \"email\",\n" +
                "                \"courses\": []}";

    }

    @Test
    void findAllTest() throws Exception {
        when(lecturerService.findAll()).thenReturn(lecturers);
        this.mockMvc.perform(get("/lecturer/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(findAll));
    }

    @Test
    void getOne() throws Exception {
        when(lecturerService.findLecturerById("2")).thenReturn(lecturer2);
        this.mockMvc.perform(get("/lecturer/get?netId=2"))
                .andExpect(status().isOk())
                .andExpect(content().json(findOne));
    }

    @Test
    void getOwnCourses() throws Exception {
        when(lecturerService.getOwnCourses("1")).thenReturn(courses);
        this.mockMvc.perform(get("/lecturer/courses/getOwnCourses?netId=1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    \"CSE2215\",\n" +
                        "    \"CSE2315\"\n" +
                        "]"));
    }

//    @Test
//    void getSpecificCourse() throws Exception {
//        Course course = new Course("CSE2215", new ArrayList<>(), 20);
//        when(lecturerService.getSpecificCourseOfLecturer("1", "CSE2215")).thenReturn(course);
//        this.mockMvc.perform(get("/lecturer/courses/getSpecificCourse?netId=1&courseId=CSE2215"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\n" +
//                        "   \"id\":\"CSE2215\",\n" +
//                        "   \"size\":20,\n" +
//                        "   \"numberOfTa\":1,\n" +
//                        "   \"candidateTas\":[\n" +
//                        "      \n" +
//                        "   ]\n" +
//                        "}"));
//    }

}
