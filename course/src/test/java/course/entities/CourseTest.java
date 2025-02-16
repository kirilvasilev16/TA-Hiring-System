package course.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import course.controllers.CourseCreationBody;
import course.services.StudentService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CourseTest {

    private transient Course course;
    private transient Set<String> lecturerSet;
    private transient LocalDateTime startingDate;
    private transient String courseId;
    private transient String courseName;
    private transient int courseSize;
    private transient int quarter;

    private final transient String lecturer1 = "lecturer1";
    private final transient String courseId1 = "CSE2115-2021";
    private final transient String lecturer2 = "lecturer2";
    private final transient String student1 = "student1";

    @BeforeEach
    void setUp() {
        lecturerSet = new HashSet<>();
        lecturerSet.add(lecturer1);
        startingDate = LocalDateTime.of(LocalDate.of(2021, 11, 7), LocalTime.NOON);
        courseSize = 500;
        quarter = 2;
        courseId = courseId1;
        courseName = "SEM";

        course = new Course(courseId, courseName, courseSize, lecturerSet, startingDate, quarter);
    }

    @Test
    void getCourseId() {
        assertEquals(courseId, course.getCourseId());
    }

    @Test
    void setCourseId() {
        String newCourseId = "CSE2115-2020";
        course.setCourseId(newCourseId);
        assertEquals(newCourseId, course.getCourseId());
    }

    @Test
    void getName() {
        assertEquals(courseName, course.getName());
    }

    @Test
    void setName() {
        String newName = "Software Engineering Method";
        course.setName(newName);
        assertEquals(newName, course.getName());
    }

    @Test
    void getStartingDate() {
        assertEquals(startingDate, course.getStartingDate());
    }

    @Test
    void setStartingDate() {
        LocalDateTime newDate = LocalDateTime.of(LocalDate.of(2021, 11, 6), LocalTime.NOON);
        course.setStartingDate(newDate);
        assertEquals(newDate, course.getStartingDate());
    }

    @Test
    void getCourseSize() {
        assertEquals(courseSize, course.getCourseSize());
    }

    @Test
    void setCourseSize() {
        int newSize = 600;
        int required = newSize / 20;
        course.setCourseSize(600);
        assertEquals(newSize, course.getCourseSize());
        assertEquals(required, course.getRequiredTas());
    }

    @Test
    void getRequiredTas() {
        int required = courseSize / 20;
        assertEquals(required, course.getRequiredTas());
    }

    @Test
    void getLecturerSet() {
        assertEquals(lecturerSet, course.getLecturerSet());
    }

    @Test
    void setLecturerSet() {
        Set<String> newLecturerSet = new HashSet<>();
        newLecturerSet.add(lecturer1);
        newLecturerSet.add(lecturer2);

        course.setLecturerSet(newLecturerSet);
        assertEquals(newLecturerSet, course.getLecturerSet());
    }

    @Test
    void getCandidateTas() {
        assertTrue(course.getCandidateTas().isEmpty());
    }

    @Test
    void setCandidateTas() {
        Set<String> newCandidateTas = new HashSet<>();
        newCandidateTas.add(student1);
        newCandidateTas.add("student2");
        course.setCandidateTas(newCandidateTas);
        assertEquals(newCandidateTas, course.getCandidateTas());
    }

    @Test
    void getHiredTas() {
        assertTrue(course.getHiredTas().isEmpty());
    }

    @Test
    void setHiredTas() {
        Set<String> newHiredTas = new HashSet<>();
        newHiredTas.add(student1);
        newHiredTas.add("student2");
        course.setHiredTas(newHiredTas);
        assertEquals(newHiredTas, course.getHiredTas());
    }

    @Test
    void testEqualsTrue() {
        Set<String> lecturerSetOther = new HashSet<>();
        lecturerSetOther.add(lecturer1);
        LocalDateTime startingDateOther = LocalDateTime.of(LocalDate.of(2021, 11, 7),
                LocalTime.NOON);
        int courseSizeOther = 500;
        int quarterOther = 2;
        String courseIdOther = courseId1;
        String courseNameOther = courseName;

        Course courseOther = new Course(courseIdOther, courseNameOther,
                courseSizeOther, lecturerSetOther, startingDateOther, quarterOther);

        assertEquals(course, courseOther);
    }

    @Test
    void testEqualsFalse() {
        Set<String> lecturerSetOther = new HashSet<>();
        lecturerSetOther.add(lecturer2);
        LocalDateTime startingDateOther = LocalDateTime.of(LocalDate.of(2021, 11, 6),
                LocalTime.NOON);
        int courseSizeOther = 200;
        int quarterOther = 4;
        String courseIdOther = "CSE2115-2020";
        String courseNameOther = courseName;

        Course courseOther = new Course(courseIdOther, courseNameOther,
                courseSizeOther, lecturerSetOther, startingDateOther, quarterOther);

        assertNotEquals(course, courseOther);
    }

    @Test
    void testEqualsFalse1() {
        Set<String> lecturerSetOther = new HashSet<>();
        lecturerSetOther.add(lecturer1);
        LocalDateTime startingDateOther = LocalDateTime.of(LocalDate.of(2021, 11, 7),
                LocalTime.NOON);
        int courseSizeOther = 500;
        int quarterOther = 2;
        String courseIdOther = courseId1;
        String courseNameOther = "otherName";

        Course courseOther = new Course(courseIdOther, courseNameOther,
                courseSizeOther, lecturerSetOther, startingDateOther, quarterOther);

        assertNotEquals(course, courseOther);
    }

    @Test
    void testEqualsFalse2() {
        Set<String> lecturerSetOther = new HashSet<>();
        lecturerSetOther.add(lecturer1);
        LocalDateTime startingDateOther = LocalDateTime.of(LocalDate.of(2021, 11, 7),
                LocalTime.NOON);
        int courseSizeOther = 200;
        int quarterOther = 2;
        String courseIdOther = courseId1;
        String courseNameOther = courseName;

        Course courseOther = new Course(courseIdOther, courseNameOther,
                courseSizeOther, lecturerSetOther, startingDateOther, quarterOther);

        assertNotEquals(course, courseOther);
    }

    @Test
    void testEqualsFalse3() {
        Set<String> lecturerSetOther = new HashSet<>();
        lecturerSetOther.add(lecturer1);
        LocalDateTime startingDateOther = LocalDateTime.of(LocalDate.of(2021, 11, 7),
                LocalTime.NOON);
        int courseSizeOther = 500;
        int quarterOther = 4;
        String courseIdOther = courseId1;
        String courseNameOther = courseName;

        Course courseOther = new Course(courseIdOther, courseNameOther,
                courseSizeOther, lecturerSetOther, startingDateOther, quarterOther);

        assertNotEquals(course, courseOther);
    }

    @Test
    void testEqualsFalse4() {
        Set<String> lecturerSetOther = new HashSet<>();
        lecturerSetOther.add(lecturer1);
        LocalDateTime startingDateOther = LocalDateTime.of(LocalDate.of(2021, 10, 7),
                LocalTime.NOON);
        int courseSizeOther = 500;
        int quarterOther = 2;
        String courseIdOther = courseId1;
        String courseNameOther = courseName;

        Course courseOther = new Course(courseIdOther, courseNameOther,
                courseSizeOther, lecturerSetOther, startingDateOther, quarterOther);

        assertNotEquals(course, courseOther);
    }

    @Test
    void testEqualsFalse5() {
        Set<String> lecturerSetOther = new HashSet<>();
        lecturerSetOther.add(lecturer2);
        LocalDateTime startingDateOther = LocalDateTime.of(LocalDate.of(2021, 11, 7),
                LocalTime.NOON);
        int courseSizeOther = 500;
        int quarterOther = 2;
        String courseIdOther = courseId1;
        String courseNameOther = courseName;

        Course courseOther = new Course(courseIdOther, courseNameOther,
                courseSizeOther, lecturerSetOther, startingDateOther, quarterOther);

        assertNotEquals(course, courseOther);
    }

    @Test
    void testEqualsFalse6() {
        Set<String> lecturerSetOther = new HashSet<>();
        lecturerSetOther.add(lecturer1);
        LocalDateTime startingDateOther = LocalDateTime.of(LocalDate.of(2021, 11, 7),
                LocalTime.NOON);
        int courseSizeOther = 500;
        int quarterOther = 2;
        String courseIdOther = courseId1;
        String courseNameOther = courseName;

        Course courseOther = new Course(courseIdOther, courseNameOther,
                courseSizeOther, lecturerSetOther, startingDateOther, quarterOther);

        Set<String> candidateSet = new HashSet<>();
        candidateSet.add(student1);

        StudentService.addCandidateSet(courseOther, candidateSet);

        assertNotEquals(course, courseOther);
    }

    @Test
    void testEqualsFalse7() {
        Set<String> lecturerSetOther = new HashSet<>();
        lecturerSetOther.add(lecturer1);
        LocalDateTime startingDateOther = LocalDateTime.of(LocalDate.of(2021, 11, 7),
                LocalTime.NOON);
        int courseSizeOther = 500;
        int quarterOther = 2;
        String courseIdOther = courseId1;
        String courseNameOther = courseName;

        Course courseOther = new Course(courseIdOther, courseNameOther,
                courseSizeOther, lecturerSetOther, startingDateOther, quarterOther);

        Set<String> taSet = new HashSet<>();
        taSet.add(student1);

        StudentService.addTaSet(courseOther, taSet);

        assertNotEquals(course, courseOther);
    }

    @Test
    void testEqualsTrueSelf() {
        assertEquals(course, course);
    }

    @Test
    void testEqualsFalseNull() {
        assertNotEquals(course, null);
    }

    @Test
    void testEqualsFalseOtherObject() {
        CourseCreationBody courseOther = new CourseCreationBody(
                courseId, courseName, startingDate, lecturerSet, courseSize, quarter);

        assertNotEquals(course, courseOther);
    }


    @Test
    void getQuarter() {
        assertEquals(quarter, course.getQuarter());
    }

    @Test
    void setQuarter() {
        int newQuarter = 1;
        course.setQuarter(newQuarter);

        assertEquals(newQuarter, course.getQuarter());
    }

    @Test
    void testHashCodeEqual() {
        Set<String> lecturerSetOther = new HashSet<>();
        lecturerSetOther.add(lecturer1);
        LocalDateTime startingDateOther = LocalDateTime.of(LocalDate.of(2021, 11, 7),
                LocalTime.NOON);
        int courseSizeOther = 500;
        int quarterOther = 2;
        String courseIdOther = courseId1;
        String courseNameOther = courseName;

        Course courseOther = new Course(courseIdOther, courseNameOther,
                courseSizeOther, lecturerSetOther, startingDateOther, quarterOther);

        assertEquals(course.hashCode(), courseOther.hashCode());
    }

    @Test
    void testHashCodeNotEqual() {
        Set<String> lecturerSetOther = new HashSet<>();
        lecturerSetOther.add(lecturer2);
        LocalDateTime startingDateOther = LocalDateTime.of(LocalDate.of(2021, 11, 6),
                LocalTime.NOON);
        int courseSizeOther = 200;
        int quarterOther = 4;
        String courseIdOther = "CSE2115-2020";
        String courseNameOther = courseName;

        Course courseOther = new Course(courseIdOther, courseNameOther,
                courseSizeOther, lecturerSetOther, startingDateOther, quarterOther);

        assertNotEquals(course.hashCode(), courseOther.hashCode());
    }


}