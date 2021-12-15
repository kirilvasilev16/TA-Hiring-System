package nl.tudelft.sem.student.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import java.util.*;
import nl.tudelft.sem.student.entities.Student;
import nl.tudelft.sem.student.exceptions.StudentNotEligibleException;
import nl.tudelft.sem.student.exceptions.StudentNotFoundException;
import nl.tudelft.sem.student.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StudentServiceTest {

    private transient Student student;
    private transient Optional<Student> optionalStudent;
    private transient StudentRepository studentRepository; // mocked
    private transient StudentService studentService; // not mocked

    @BeforeEach
    void setUp() {
        student = new Student("ohageman", "Oisín");
        Map<String, Float> passed = new HashMap<>();
        passed.put("cse1000", 7.0f);
        passed.put("cse2000", 8.0f);
        passed.put("cse3000", 6.0f);
        passed.put("cse4000", 7.0f);
        Set<String> candidate = new HashSet<>();
        candidate.add("cse2000");
        Set<String> ta = new HashSet<>();
        ta.add("cse4000");
        student.setPassedCourses(passed);
        student.setCandidateCourses(candidate);
        student.setTaCourses(ta);
        optionalStudent = Optional.of(student);

        studentRepository = Mockito.mock(StudentRepository.class);
        studentService = new StudentService(studentRepository);
        Mockito.when(studentRepository.findStudentByNetId("ohageman")).thenReturn(optionalStudent);
        Mockito.when(studentRepository.findStudentByNetId(not(eq("ohageman")))).thenReturn(Optional.empty());
        Mockito.when(studentRepository.findAll()).thenReturn(List.of(student));
    }

    @Test
    void getStudentTest() {
        Student testStudent = studentService.getStudent("ohageman");
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
        studentIds.add("ohageman");
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
        Map<String, Float> testPassedCourses = studentService.getPassedCourses("ohageman");
        assertEquals(passedCourses, testPassedCourses);
    }

    @Test
    void getCandidateCoursesTest() {
        Set<String> candidateCourses = student.getCandidateCourses();
        Set<String> testCandidateCourses = studentService.getCandidateCourses("ohageman");
        assertEquals(candidateCourses, testCandidateCourses);
    }

    @Test
    void getTaCoursesTest() {
        Set<String> taCourses = student.getTaCourses();
        Set<String> testTaCourses = studentService.getTaCourses("ohageman");
        assertEquals(taCourses, testTaCourses);
    }

    @Test
    void applyTest() {
        Set<String> candidateCourses = new HashSet<>();
        candidateCourses.add("cse1000");
        candidateCourses.add("cse2000");
        Set<String> testCandidateCourses = studentService.apply("ohageman", "cse1000").getCandidateCourses();
        assertEquals(candidateCourses, testCandidateCourses);
    }

    @Test
    void applyExceptionTest() {
        assertThrows(StudentNotEligibleException.class, () -> studentService.apply("ohageman", "cse5000"));
    }

    @Test
    void acceptTest() {
        Set<String> candidateCourses = new HashSet<>();
        Set<String> taCourses = new HashSet<>();
        taCourses.add("cse2000");
        taCourses.add("cse4000");
        Student testStudent = studentService.accept("ohageman", "cse2000");
        Set<String> testCandidateCourses = testStudent.getCandidateCourses();
        Set<String> testTaCourses = testStudent.getTaCourses();
        assertEquals(candidateCourses, testCandidateCourses);
        assertEquals(taCourses, testTaCourses);
    }

    @Test
    void acceptExceptionTest() {
        assertThrows(StudentNotEligibleException.class, () -> studentService.accept("ohageman", "cse3000"));
    }

}
