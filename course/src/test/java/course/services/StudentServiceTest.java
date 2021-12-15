package course.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import course.entities.Course;
import course.exceptions.InvalidCandidateException;
import course.exceptions.InvalidHiringException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class StudentServiceTest {

    private transient Course course;
    private transient Set<String> lecturerSet;
    private transient LocalDateTime startingDate;
    private transient String courseId;
    private transient String courseName;
    private transient int courseSize;

    private transient Set<String> candidateSet;
    private transient Set<String> hireSet;

    private transient String student1 = "student1";
    private transient String student2 = "student2";
    private transient String student3 = "student3";

    @BeforeEach
    void setUp() {

        lecturerSet = new HashSet<>();
        lecturerSet.add("lecturer1");
        startingDate = LocalDateTime.of(LocalDate.of(2021, 11, 7), LocalTime.NOON);
        courseSize = 500;
        courseId = "CSE2115-2021";
        courseName = "SEM";

        course = new Course(courseId, courseName, courseSize, lecturerSet, startingDate);

        candidateSet = new HashSet<>();
        candidateSet.add(student1);
        candidateSet.add(student2);

        hireSet = new HashSet<>();
        hireSet.add(student3);
        hireSet.add("student4");

    }

    @Test
    void getCandidates() {
        assertTrue(StudentService.getCandidates(course).isEmpty());
    }

    @Test
    void addCandidateSet() {
        StudentService.addCandidateSet(course, candidateSet);
        assertEquals(candidateSet, StudentService.getCandidates(course));
    }

    @Test
    void addCandidate() {
        StudentService.addCandidate(course, student1);
        StudentService.addCandidate(course, student2);
        assertEquals(candidateSet, StudentService.getCandidates(course));
    }

    @Test
    void addCandidateInvalid() { //student already hired
        StudentService.addTaSet(course, hireSet);
        assertThrows(InvalidCandidateException.class, () -> {
            StudentService.addCandidate(course, student3);
        });
    }

    @Test
    void removeCandidate() {
        StudentService.addCandidateSet(course, candidateSet);
        assertEquals(candidateSet, StudentService.getCandidates(course));

        StudentService.removeCandidate(course, student1);
        Set<String> expect = new HashSet<>();
        expect.add(student2);
        assertEquals(expect, StudentService.getCandidates(course));

    }

    @Test
    void removeCandidateInvalid() { //student not a candidate
        assertThrows(InvalidCandidateException.class, () -> {
            StudentService.removeCandidate(course, student1);
        });
    }

    @Test
    void containsCandidateTrue() {
        StudentService.addCandidate(course, student1);
        assertTrue(StudentService.containsCandidate(course, student1));
    }

    @Test
    void containsCandidateFalse() {
        assertFalse(StudentService.containsCandidate(course, student1));
    }

//    @Test
//    void hireTa() { // TODO: add mocks for management and lecturer interaction
//        String studentToHire = student2;
//        StudentService.addCandidateSet(course, candidateSet);
//        StudentService.addTaSet(course, hireSet);
//
//        assertTrue(StudentService.hireTa(course, studentToHire, 1));
//        assertTrue(StudentService.containsTa(course, studentToHire));
//        assertFalse(StudentService.containsCandidate(course, studentToHire));
//    }

    @Test
    void hireTaInvalidAlreadyHired() {
        String studentToHire = student3;
        StudentService.addCandidateSet(course, candidateSet);
        StudentService.addTaSet(course, hireSet);

        assertThrows(InvalidHiringException.class, () -> {
            StudentService.hireTa(course, studentToHire, 1);
        });
    }

    @Test
    void hireTaInvalidNotInCourse() {
        String studentToHire = "student5";
        StudentService.addCandidateSet(course, candidateSet);
        StudentService.addTaSet(course, hireSet);

        assertThrows(InvalidHiringException.class, () -> {
            StudentService.hireTa(course, studentToHire, 1);
        });
    }


    @Test
    void getTaSet() {
        assertTrue(StudentService.getTaSet(course).isEmpty());
    }

    @Test
    void addTaSet() {
        StudentService.addTaSet(course, hireSet);
        assertEquals(hireSet, StudentService.getTaSet(course));
    }

    @Test
    void removeTaTrue() {
        StudentService.addTaSet(course, hireSet);
        assertTrue(StudentService.removeTa(course, student3));

        Set<String> expect = new HashSet<>();
        expect.add("student4");
        assertEquals(expect, StudentService.getTaSet(course));
    }

    @Test
    void removeTaFalse() {
        StudentService.addTaSet(course, hireSet);
        assertFalse(StudentService.removeTa(course, "student5"));

        assertEquals(hireSet, StudentService.getTaSet(course));
    }

    @Test
    void containsTaTrue() {
        StudentService.addTaSet(course, hireSet);
        assertTrue(StudentService.containsTa(course, student3));
    }

    @Test
    void containsTaFalse() {
        StudentService.addTaSet(course, hireSet);
        assertFalse(StudentService.containsTa(course, "student5"));
    }

    @Test
    void enoughTasTrue() {
        course.setRequiredTas(2);
        StudentService.addTaSet(course, hireSet);

        assertTrue(StudentService.enoughTas(course));
    }

    @Test
    void enoughTasFalse() {
        course.setRequiredTas(3);
        StudentService.addTaSet(course, hireSet);

        assertFalse(StudentService.enoughTas(course));
    }

    @Test
    void hiredTas() {
        StudentService.addTaSet(course, hireSet);
        assertEquals(2, StudentService.hiredTas(course));
    }
}