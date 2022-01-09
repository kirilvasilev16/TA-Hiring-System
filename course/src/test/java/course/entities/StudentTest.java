package course.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import course.exceptions.CourseNotPassedException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentTest {

    private transient Student student;
    private transient String netId;
    private transient Map<String, Float> passedCourses;
    private transient Set<String> taCourses;

    @BeforeEach
    void setUp() {
        netId = "student1";
        passedCourses = new HashMap<>();
        passedCourses.put("CSE2100", 9f);
        taCourses = new HashSet<>(Arrays.asList("CSE1005-2020"));
        student = new Student(netId, passedCourses, taCourses);
    }

    @Test
    void getNetId() {
        assertEquals(netId, student.getNetId());
    }

    @Test
    void setNetId() {
        String newNetId = "newId";
        student.setNetId(newNetId);
        assertEquals(newNetId, student.getNetId());
    }

    @Test
    void getPassedCourses() {
        assertEquals(passedCourses, student.getPassedCourses());
    }

    @Test
    void setPassedCourses() {
        Map<String, Float> newPassedCourses = Map.of("CSE1105-2021", 5f);
        student.setPassedCourses(newPassedCourses);
        assertEquals(newPassedCourses, student.getPassedCourses());
    }

    @Test
    void getTaCourses() {
        assertEquals(taCourses, student.getTaCourses());
    }

    @Test
    void setTaCourses() {
        Set<String> newTaCourses = new HashSet<>(Arrays.asList("CSE2015-2020"));
        student.setTaCourses(newTaCourses);
        assertEquals(newTaCourses, student.getTaCourses());
    }

    @Test
    void getHighestGradeAchieved() {
        assertEquals(9f, student.getHighestGradeAchieved("CSE2100-2022"));
    }

    @Test
    void getHighestGradeAchievedNull() {
        String c = "CSE1300-2020";
        String strip = "CSE1300";
        student.getPassedCourses().put(strip, null);

        assertThrows(CourseNotPassedException.class, () -> {
            student.getHighestGradeAchieved(c);
        });
    }

    @Test
    void getHighestGradeAchievedLower() {
        String c = "CSE1300-2020";
        String strip = "CSE1300";
        student.getPassedCourses().put(strip, -1f);

        assertThrows(CourseNotPassedException.class, () -> {
            student.getHighestGradeAchieved(c);
        });
    }
}