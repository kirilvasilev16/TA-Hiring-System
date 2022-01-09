package student.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import student.entities.Student;
import student.services.StudentService;

public class StudentControllerTest {

    private transient Student student;
    private final transient String netId = "ohageman";
    private final transient String name = "Oisín";
    private transient Map<String, Float> passedCourses;
    private transient Set<String> candidateCourses;
    private transient Set<String> taCourses;

    private transient StudentService studentService; // mocked
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
        candidateCourses.add("CSE1400-2021");
        taCourses = new HashSet<>();
        taCourses.add("CSE1105-2022");
        student.setPassedCourses(passedCourses);
        student.setCandidateCourses(candidateCourses);
        student.setTaCourses(taCourses);

        studentService = Mockito.mock(StudentService.class);
        studentController = new StudentController(studentService);
        Mockito.when(studentService.getStudent("ohageman")).thenReturn(student);
        Mockito.when(studentService.getAll()).thenReturn(List.of(student));
    }

    @Test
    void getStudentTest() {
        assertEquals(student, studentController.getStudent("ohageman"));
    }

    @Test
    void getAllTest() {
        List<Student> allStudents = new ArrayList<>();
        allStudents.add(student);
        assertEquals(allStudents, studentController.getAll());
    }

}
