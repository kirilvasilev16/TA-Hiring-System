package course.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import course.entities.Course;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class LecturerServiceTest {

    private transient Course course;
    private transient Set<String> lecturerSet;
    private transient LocalDateTime startingDate;
    private transient String courseId;
    private transient String courseName;
    private transient int courseSize;

    private transient String lecturer1 = "lecturer1";

    @BeforeEach
    void setUp() {

        lecturerSet = new HashSet<>();
        lecturerSet.add(lecturer1);
        startingDate = LocalDateTime.of(LocalDate.of(2021, 11, 7), LocalTime.NOON);
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

        String lecturer2 = "lecturer2";
        String lecturer3 = "lecturer3";

        Set<String> expect = new HashSet<>();
        expect.add(lecturer1);
        expect.add(lecturer2);
        expect.add(lecturer3);

        Set<String> add = new HashSet<>();
        add.add(lecturer2);
        add.add(lecturer3);

        LecturerService.addLecturerSet(course, add);
        assertEquals(expect, LecturerService.getLecturerSet(course));
    }

    @Test
    void addLecturer() {
        String lecturer2 = "lecturer2";

        Set<String> expect = new HashSet<>();
        expect.add(lecturer1);
        expect.add(lecturer2);

        LecturerService.addLecturer(course, lecturer2);
        assertEquals(expect, LecturerService.getLecturerSet(course));
    }

    @Test
    void containsLecturerTrue() {
        assertTrue(LecturerService.containsLecturer(course, lecturer1));
    }

    @Test
    void containsLecturerFalse() {
        String lecturer2 = "lecturer2";

        assertFalse(LecturerService.containsLecturer(course, lecturer2));
    }
}