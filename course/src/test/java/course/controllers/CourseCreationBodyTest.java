package course.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




class CourseCreationBodyTest {

    private transient String courseId;
    private transient String name;
    private transient Calendar startingDate;
    private transient Integer courseSize;
    private transient Integer quarter;
    private transient Set<String> lecturerSet;
    private transient CourseCreationBody course;

    @BeforeEach
    void setUp() {
        courseId = "CSE1200";
        name = "Calculus";
        startingDate = new Calendar.Builder().setDate(2021, 11, 7).build();
        courseSize = 100;
        quarter = 2;
        lecturerSet = new HashSet<>();
        lecturerSet.add("lecturer1");
        lecturerSet.add("lecturer2");

        course = new CourseCreationBody(courseId,
                name, startingDate, lecturerSet, courseSize, quarter);
    }

    @Test
    void getCourseId() {
        assertEquals(courseId, course.getCourseId());
    }

    @Test
    void setCourseId() {
        String newId = "CSE1400";
        course.setCourseId(newId);
        assertEquals(newId, course.getCourseId());
    }

    @Test
    void getName() {
        assertEquals(name, course.getName());
    }

    @Test
    void setName() {
        String newName = "Computer Organization";
        course.setName(newName);
        assertEquals(newName, course.getName());
    }

    @Test
    void getStartingDate() {
        assertEquals(startingDate, course.getStartingDate());
    }

    @Test
    void setStartingDate() {
        Calendar newDate = new Calendar.Builder().setDate(2020, 5, 6).build();
        course.setStartingDate(newDate);

        assertEquals(newDate, course.getStartingDate());
    }

    @Test
    void getLecturerSet() {
        assertEquals(lecturerSet, course.getLecturerSet());
    }

    @Test
    void setLecturerSet() {
        Set<String> newSet = new HashSet<>();
        newSet.add("lecturer3");

        course.setLecturerSet(newSet);

        assertEquals(newSet, course.getLecturerSet());
    }

    @Test
    void getCourseSize() {
        assertEquals(courseSize, course.getCourseSize());
    }

    @Test
    void setCourseSize() {
        int newSize = 200;
        course.setCourseSize(newSize);

        assertEquals(newSize, course.getCourseSize());
    }
}