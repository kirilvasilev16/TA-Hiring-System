package student.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import student.communication.CourseCommunication;
import student.entities.Student;
import student.exceptions.StudentNotEligibleException;
import student.exceptions.StudentNotFoundException;
import student.repositories.StudentRepository;


public class StudentServiceTest {

    private transient Student student;
    private final transient String netId = "ohageman";
    private final transient String name = "Oisín";
    private final transient String courseCode = "CSE2115-2022";
    private transient Map<String, Float> passedCourses;
    private transient Set<String> candidateCourses;
    private transient Set<String> taCourses;
    private transient StudentRepository studentRepository; // mocked
    private transient CourseCommunication courseCommunication; // mocked
    private transient StudentService studentService; // not mocked

    @BeforeEach
    void setUp() {
        passedCourses = new HashMap<>();
        passedCourses.put("CSE2115", 10.0f);
        passedCourses.put("CSE1400", 10.0f);
        passedCourses.put("CSE1105", 10.0f);
        student = new Student(netId, name);
        candidateCourses = new HashSet<>();
        candidateCourses.add(courseCode);
        taCourses = new HashSet<>();
        taCourses.add("CSE1105-2022");
        student.setPassedCourses(passedCourses);
        student.setCandidateCourses(candidateCourses);
        student.setTaCourses(taCourses);

        studentRepository = Mockito.mock(StudentRepository.class);
        courseCommunication = Mockito.mock(CourseCommunication.class);
        studentService = new StudentService(studentRepository, courseCommunication);
        Optional<Student> optionalStudent = Optional.of(student);
        Mockito.when(studentRepository.findStudentByNetId(netId)).thenReturn(optionalStudent);
        Mockito.when(studentRepository.findStudentByNetId(not(eq(netId))))
                .thenReturn(Optional.empty());
        Mockito.when(studentRepository.findAll()).thenReturn(List.of(student));
        Mockito.when(courseCommunication.checkApplyRequirement(any(), any(), any()))
                .thenReturn(true);
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
        candidateCourses.add(courseCode);
        candidateCourses.add("CSE1400-2021");
        Set<String> testCandidateCourses =
                studentService.apply(netId, "CSE1400-2021").getCandidateCourses();
        assertEquals(candidateCourses, testCandidateCourses);
    }

    @Test
    void applyExceptionTest() {
        assertThrows(StudentNotEligibleException.class,
                () -> studentService.apply(netId, "CSE9999-2022"));
    }

    @Test
    void acceptTest() {
        Set<String> candidateCourses = new HashSet<>();
        Set<String> taCourses = new HashSet<>();
        taCourses.add("CSE1105-2022");
        taCourses.add(courseCode);
        Student testStudent = studentService.accept(netId, courseCode);
        Set<String> testCandidateCourses = testStudent.getCandidateCourses();
        Set<String> testTaCourses = testStudent.getTaCourses();
        assertEquals(candidateCourses, testCandidateCourses);
        assertEquals(taCourses, testTaCourses);
    }

    @Test
    void acceptExceptionTest() {
        assertThrows(StudentNotEligibleException.class,
                () -> studentService.accept(netId, "CSE1400-2021"));
    }

}
