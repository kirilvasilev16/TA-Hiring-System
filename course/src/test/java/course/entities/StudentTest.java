package course.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private transient Student student;
    private transient String netId;
    private transient Map<String, Float> passedCourses;
    private transient Set<String> taCourses;

    @BeforeEach
    void setUp() {
        netId = "student1";
        passedCourses = Map.of("CSE2100-2020", 9f,
                "CSE2100-2021", 4f,
                "CSE2100-2019", 8f);
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
        assertEquals(8f, student.getHighestGradeAchieved("CSE2100-2020"));
    }
}