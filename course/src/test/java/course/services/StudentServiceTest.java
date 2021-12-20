package course.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import course.entities.Course;
import course.entities.Management;
import course.exceptions.DeadlinePastException;
import course.exceptions.InvalidCandidateException;
import course.exceptions.InvalidHiringException;
import course.exceptions.InvalidStrategyException;
import course.exceptions.TooManyCoursesException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class StudentServiceTest {

    private transient Course course;
    private transient Set<String> lecturerSet;
    private transient LocalDateTime startingDate;
    private transient String courseId;
    private transient String courseName;
    private transient int courseSize;
    private transient int quarter;

    private transient Set<String> candidateSet;
    private transient Set<String> hireSet;

    private transient String student1 = "student1";
    private transient String student2 = "student2";
    private transient String student3 = "student3";
    private transient String lecturer1 = "lecturer1";
    private transient LocalDateTime applicationDate;

    private transient CommunicationService mockComm;

    @BeforeEach
    void setUp() {

        lecturerSet = new HashSet<>();
        lecturerSet.add(lecturer1);
        startingDate = LocalDateTime.of(LocalDate.of(2021, 12, 7), LocalTime.NOON);
        courseSize = 500;
        courseId = "CSE2115-2021";
        courseName = "SEM";
        quarter = 2;

        course = new Course(courseId, courseName, courseSize, lecturerSet, startingDate, quarter);

        candidateSet = new HashSet<>();
        candidateSet.add(student1);
        candidateSet.add(student2);

        hireSet = new HashSet<>();
        hireSet.add(student3);
        hireSet.add("student4");

        applicationDate = LocalDateTime.of(LocalDate.of(2021, 10, 6), LocalTime.NOON);
    }

    @Test
    void constructor() {
        assertNotNull(new StudentService());
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
        StudentService.addCandidate(course, student1, applicationDate);
        StudentService.addCandidate(course, student2, applicationDate);
        assertEquals(candidateSet, StudentService.getCandidates(course));
    }

    @Test
    void addCandidateInvalid() { //student already hired
        StudentService.addTaSet(course, hireSet);
        assertThrows(InvalidCandidateException.class, () -> {
            StudentService.addCandidate(course, student3, applicationDate);
        });
    }

    @Test
    void addCandidateExactly3Weeks() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(2021, 11, 16), LocalTime.NOON);
        StudentService.addCandidate(course, student1, date);
        StudentService.addCandidate(course, student2, date);
        assertEquals(candidateSet, StudentService.getCandidates(course));
    }

    @Test
    void addCandidate1DayLate() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(2021, 11, 17), LocalTime.NOON);

        assertThrows(DeadlinePastException.class, () -> {
            StudentService.addCandidate(course, student1, date);
        });
    }

    @Test
    void addCandidateLate() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(2021, 11, 17), LocalTime.NOON);

        assertThrows(DeadlinePastException.class, () -> {
            StudentService.addCandidate(course, student1, date);
        });
    }

    @Test
    void addCandidateYearCrossoverRegion() {

        LocalDateTime newDate = LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.NOON);
        LocalDateTime date = LocalDateTime.of(LocalDate.of(2021, 12, 11), LocalTime.NOON);

        course.setStartingDate(newDate);

        StudentService.addCandidate(course, student1, date);
        StudentService.addCandidate(course, student2, date);
        assertEquals(candidateSet, StudentService.getCandidates(course));
    }

    @Test
    void addCandidateYearCrossoverRegionLate() {

        LocalDateTime newDate = LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.NOON);
        LocalDateTime date = LocalDateTime.of(LocalDate.of(2021, 12, 12), LocalTime.NOON);

        course.setStartingDate(newDate);

        assertThrows(DeadlinePastException.class, () -> {
            StudentService.addCandidate(course, student1, date);
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
        StudentService.addCandidate(course, student1, applicationDate);
        assertTrue(StudentService.containsCandidate(course, student1));
    }

    @Test
    void containsCandidateFalse() {
        assertFalse(StudentService.containsCandidate(course, student1));
    }


    @Test
    void hireTa() { // TODO: add mocks for management and lecturer interaction
        mockComm = Mockito.mock(CommunicationService.class);
        Mockito.when(mockComm.createManagement(Mockito.any(),
                        Mockito.anyString(),
                        Mockito.anyFloat()))
                .thenReturn(new Management());
        String studentToHire = student2;
        StudentService.addCandidateSet(course, candidateSet);
        StudentService.addTaSet(course, hireSet);
        assertTrue(StudentService.hireTa(course, studentToHire, 1, mockComm));
        assertTrue(StudentService.containsTa(course, studentToHire));
        assertFalse(StudentService.containsCandidate(course, studentToHire));
    }

    @Test
    void hireTaInvalidAlreadyHired() {
        mockComm = Mockito.mock(CommunicationService.class);
        Mockito.when(mockComm.createManagement(Mockito.any(),
                        Mockito.anyString(),
                        Mockito.anyFloat()))
                .thenReturn(new Management());

        String studentToHire = student3;
        StudentService.addCandidateSet(course, candidateSet);
        StudentService.addTaSet(course, hireSet);

        assertThrows(InvalidHiringException.class, () -> {
            StudentService.hireTa(course, studentToHire, 1, mockComm);
        });
    }

    @Test
    void hireTaInvalidNotInCourse() {
        mockComm = Mockito.mock(CommunicationService.class);
        Mockito.when(mockComm.createManagement(Mockito.any(),
                        Mockito.anyString(),
                        Mockito.anyFloat()))
                .thenReturn(new Management());

        String studentToHire = "student5";
        StudentService.addCandidateSet(course, candidateSet);
        StudentService.addTaSet(course, hireSet);

        assertThrows(InvalidHiringException.class, () -> {
            StudentService.hireTa(course, studentToHire, 1, mockComm);
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
    void hiredTas() {
        StudentService.addTaSet(course, hireSet);
        assertEquals(2, StudentService.hiredTas(course));
    }

    @Test
    void withinQuarterCapacity() {
        Set<Course> courseSet = new HashSet<>();
        Course c = new Course();
        c.setCourseId("CSE0001-2021");
        c.setQuarter(2);
        courseSet.add(c);
        c = new Course();
        c.setCourseId("CSE0002-2021");
        c.setQuarter(2);
        courseSet.add(c);
        c = new Course();
        c.setCourseId("CSE0003-2021");
        c.setQuarter(2);
        courseSet.add(c);
        c = new Course();
        c.setCourseId("CSE0001-2020");
        c.setQuarter(2);
        courseSet.add(c);
        assertDoesNotThrow(() -> {
            StudentService.checkQuarterCapacity(courseSet);
        });
    }

    @Test
    void outsideQuarterCapacity() {
        Set<Course> courseSet = new HashSet<>();
        Course c = new Course();
        c.setCourseId("CSE0001-2021");
        c.setQuarter(2);
        courseSet.add(c);
        c = new Course();
        c.setCourseId("CSE0002-2021");
        c.setQuarter(2);
        courseSet.add(c);
        c = new Course();
        c.setCourseId("CSE0003-2021");
        c.setQuarter(2);
        courseSet.add(c);
        c = new Course();
        c.setCourseId("CSE0004-2021");
        c.setQuarter(2);
        courseSet.add(c);
        assertThrows(TooManyCoursesException.class, () -> {
            StudentService.checkQuarterCapacity(courseSet);
        });
    }

    @Test
    void taRecommendationTestEmpty() {
        mockComm = Mockito.mock(CommunicationService.class);
        Mockito.when(mockComm.getStudents(Mockito.any()))
                .thenReturn(new HashSet<>());

        assertTrue(StudentService
                .getTaRecommendationList(course, "grade", mockComm).isEmpty());
        assertTrue(StudentService
                .getTaRecommendationList(course, "experience", mockComm).isEmpty());
        assertTrue(StudentService
                .getTaRecommendationList(course, "rating", mockComm).isEmpty());
    }

    @Test
    void taRecommendationTestNoMethod() {
        mockComm = Mockito.mock(CommunicationService.class);
        Mockito.when(mockComm.getStudents(Mockito.any()))
                .thenReturn(new HashSet<>());

        assertThrows(InvalidStrategyException.class, () -> {
            StudentService.getTaRecommendationList(course, "random", mockComm);
        });
    }

    @Test
    void averageWorkedHoursTest() {
        Set<Float> hours = new HashSet<>(Arrays.asList(5f, 7f, 9f, 10f));
        mockComm = Mockito.mock(CommunicationService.class);
        Mockito.when(mockComm.getHoursList(Mockito.any(), Mockito.anyString()))
                .thenReturn(hours);

        assertEquals(7.75f, StudentService.getAverageWorkedHours(course, mockComm));
    }

    @Test
    void averageWorkedHoursEmpty() {
        mockComm = Mockito.mock(CommunicationService.class);
        Mockito.when(mockComm.getHoursList(Mockito.any(), Mockito.anyString()))
                .thenReturn(new HashSet<>());

        assertEquals(0, StudentService.getAverageWorkedHours(course, mockComm));
    }

    @Test
    void enoughTasTrue() {
        Set<String> taSet = new HashSet<>();

        for (int i = 0; i < course.getRequiredTas(); i++) {
            taSet.add("student" + i);
        }

        StudentService.addTaSet(course, taSet);

        assertTrue(StudentService.enoughTas(course));
    }

    @Test
    void enoughTasFalse() {
        Set<String> taSet = new HashSet<>();

        for (int i = 0; i < course.getRequiredTas() - 1; i++) {
            taSet.add("student" + i);
        }

        StudentService.addTaSet(course, taSet);

        assertFalse(StudentService.enoughTas(course));
    }
}