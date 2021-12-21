package student.services;

import student.communication.CourseCommunication;
import student.entities.Student;
import student.exceptions.StudentNotEligibleException;
import student.exceptions.StudentNotFoundException;
import student.repositories.StudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.net.ssl.SSLSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class StudentServiceTest {

    private transient Student student;
    private transient StudentRepository studentRepository; // mocked
    private transient CourseCommunication courseCommunication; // mocked
    private transient StudentService studentService; // not mocked
    private final transient String netId = "ohageman";
    private final transient String course2000 = "cse2000";

    @BeforeEach
    void setUp() {
        student = new Student(netId, "Oisín");
        Map<String, Float> passed = new HashMap<>();
        passed.put("cse1000", 7.0f);
        passed.put(course2000, 8.0f);
        passed.put("cse3000", 6.0f);
        passed.put("cse4000", 7.0f);
        Set<String> candidate = new HashSet<>();
        candidate.add(course2000);
        Set<String> ta = new HashSet<>();
        ta.add("cse4000");
        student.setPassedCourses(passed);
        student.setCandidateCourses(candidate);
        student.setTaCourses(ta);
        Optional<Student> optionalStudent = Optional.of(student);

        studentRepository = Mockito.mock(StudentRepository.class);
        courseCommunication = Mockito.mock(CourseCommunication.class);
        studentService = new StudentService(studentRepository, courseCommunication);
        Mockito.when(studentRepository.findStudentByNetId(netId)).thenReturn(optionalStudent);
        Mockito.when(studentRepository.findStudentByNetId(not(eq(netId))))
                .thenReturn(Optional.empty());
        Mockito.when(studentRepository.findAll()).thenReturn(List.of(student));
        Mockito.when(courseCommunication.checkApplyRequirement(any(), any(), any())).thenReturn(true);
    }

    @Test
    void getStudentTest() {
        Student testStudent = studentService.getStudent(netId);
        assertEquals(student, testStudent);
    }

    @Test
    void getStudentExceptionTest() {
        assertThrows(StudentNotFoundException.class, () -> studentService.getStudent("nope"));
    }

    @Test
    void getAllTest() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        List<Student> testStudents = studentService.getAll();
        assertEquals(students, testStudents);
    }

    @Test
    void getMultipleTest() {
        Set<Student> students = new HashSet<>();
        students.add(student);
        Set<String> studentIds = new HashSet<>();
        studentIds.add(netId);
        Set<Student> testStudents = studentService.getMultiple(studentIds);
        assertEquals(students, testStudents);
    }

    @Test
    void getMultipleEmptyTest() {
        Set<Student> students = new HashSet<>();
        Set<String> studentIds = new HashSet<>();
        Set<Student> testStudents = studentService.getMultiple(studentIds);
        assertEquals(students, testStudents);
    }

    @Test
    void getPassedCoursesTest() {
        Map<String, Float> passedCourses = student.getPassedCourses();
        Map<String, Float> testPassedCourses = studentService.getPassedCourses(netId);
        assertEquals(passedCourses, testPassedCourses);
    }

    @Test
    void getCandidateCoursesTest() {
        Set<String> candidateCourses = student.getCandidateCourses();
        Set<String> testCandidateCourses = studentService.getCandidateCourses(netId);
        assertEquals(candidateCourses, testCandidateCourses);
    }

    @Test
    void getTaCoursesTest() {
        Set<String> taCourses = student.getTaCourses();
        Set<String> testTaCourses = studentService.getTaCourses(netId);
        assertEquals(taCourses, testTaCourses);
    }

    @Test
    void applyTest() {
        Set<String> candidateCourses = new HashSet<>();
        candidateCourses.add("cse1000");
        candidateCourses.add(course2000);
        Set<String> testCandidateCourses =
                studentService.apply(netId, "cse1000").getCandidateCourses();
        assertEquals(candidateCourses, testCandidateCourses);
    }

    @Test
    void applyExceptionTest() {
        assertThrows(StudentNotEligibleException.class,
                () -> studentService.apply(netId, "cse5000"));
    }

    @Test
    void acceptTest() {
        Set<String> candidateCourses = new HashSet<>();
        Set<String> taCourses = new HashSet<>();
        taCourses.add(course2000);
        taCourses.add("cse4000");
        Student testStudent = studentService.accept(netId, course2000);
        Set<String> testCandidateCourses = testStudent.getCandidateCourses();
        Set<String> testTaCourses = testStudent.getTaCourses();
        assertEquals(candidateCourses, testCandidateCourses);
        assertEquals(taCourses, testTaCourses);
    }

    @Test
    void acceptExceptionTest() {
        assertThrows(StudentNotEligibleException.class,
                () -> studentService.accept(netId, "cse3000"));
    }

}
