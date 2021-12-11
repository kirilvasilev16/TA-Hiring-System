package course.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CourseTest {
    private transient Course course;
    private transient Set<String> lecturerSet;
    private transient Calendar startingDate;
    private transient String courseId;
    private transient String courseName;
    private transient int courseSize;
    private transient int quarter;

    @BeforeEach
    void setUp() {
        lecturerSet = new HashSet<>();
        lecturerSet.add("lecturer1");
        startingDate = new Calendar.Builder().setDate(2021, 11, 7).build();
        courseSize = 500;
        quarter = 2;
        courseId = "CSE2115-2021";
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
        Calendar newDate = new Calendar.Builder().setDate(2021, 11, 6).build();
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
        newLecturerSet.add("lecture1");
        newLecturerSet.add("lecturer2");

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
        newCandidateTas.add("student1");
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
        newHiredTas.add("student1");
        newHiredTas.add("student2");
        course.setHiredTas(newHiredTas);
        assertEquals(newHiredTas, course.getHiredTas());
    }
}