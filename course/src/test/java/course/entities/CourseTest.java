package course.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    Course course;
    Set<String> lecturerSet;
    Date startingDate;
    String courseId;
    String courseName;
    int courseSize;

    @BeforeEach
    void setUp() {
        lecturerSet = new HashSet<>();
        lecturerSet.add("lecturer1");

        startingDate = new Date(121, 11, 7);
        courseSize = 500;
        courseId = "CSE2115-2021";
        courseName = "SEM";

        course = new Course(courseId, courseName, courseSize, lecturerSet, startingDate);
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
        Date newDate = new Date(121, 11, 6);
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
    void setRequiredTas() {
        int required = 10;
        course.setRequiredTas(required);
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