package course.services;

import course.entities.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LecturerServiceTest {

    Course course;
    Set<String> lecturerSet;
    Calendar startingDate;
    String courseId;
    String courseName;
    int courseSize;

    @BeforeEach
    void setUp() {
        lecturerSet = new HashSet<>();
        lecturerSet.add("lecturer1");
        startingDate = new Calendar.Builder().setDate(2021, 11, 7).build();
        courseSize = 500;
        courseId = "CSE2115-2021";
        courseName = "SEM";

        course = new Course(courseId, courseName, courseSize, lecturerSet, startingDate);
    }

    @Test
    void getLecturerSet() {
        assertEquals(lecturerSet, LecturerService.getLecturerSet(course));
    }

    @Test
    void addLecturerSet() {
        Set<String> expect = new HashSet<>();
        expect.add("lecturer1");
        expect.add("lecturer2");
        expect.add("lecturer3");

        Set<String> add = new HashSet<>();
        add.add("lecturer2");
        add.add("lecturer3");

        LecturerService.addLecturerSet(course, add);
        assertEquals(expect, LecturerService.getLecturerSet(course));
    }

    @Test
    void addLecturer() {
        Set<String> expect = new HashSet<>();
        expect.add("lecturer1");
        expect.add("lecturer2");

        LecturerService.addLecturer(course, "lecturer2");
        assertEquals(expect, LecturerService.getLecturerSet(course));
    }

    @Test
    void containsLecturerTrue() {
        assertTrue(LecturerService.containsLecturer(course, "lecturer1"));
    }

    @Test
    void containsLecturerFalse() {
        assertFalse(LecturerService.containsLecturer(course, "lecturer2"));
    }
}