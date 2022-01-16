package lecturer.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;


public class StudentClassTest {
    private transient Student student1 = new Student("1");

    @Test
    void constructor() {
        assertNotNull(student1);
    }

    @Test
    void emptyConstructor() {
        Student student = new Student();
        assertNotNull(student);
    }

    @Test
    void id() {
        student1.setNetId("2");
        assertEquals("2", student1.getNetId());
    }

    @Test
    void name() {
        student1.setName("s");
        assertEquals("s", student1.getName());
    }

    @Test
    void passed() {
        Map<String, Float> map = new HashMap<>();
        student1.setPassedCourses(map);
        assertEquals(map, student1.getPassedCourses());
    }

    @Test
    void candidates() {
        Set<String> c = new HashSet<>();
        c.add("a");
        student1.setCandidateCourses(c);
        assertEquals(c, student1.getCandidateCourses());
        assertEquals(1, student1.getCandidateCourses().size());
    }

    @Test
    void tas() {
        Set<String> c = new HashSet<>();
        c.add("a");
        student1.setTaCourses(c);
        assertEquals(c, student1.getTaCourses());
        assertEquals(1, student1.getTaCourses().size());
    }
}
