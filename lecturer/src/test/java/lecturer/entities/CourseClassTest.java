package lecturer.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseClassTest {
    private transient Course course;

    @BeforeEach
    void setUp() {
        course = new Course("CSE1120", new HashSet<>(), 500);
    }

    @Test
    void testConstructor() {
        Course c = new Course();
        assertNotNull(c);
    }

    @Test
    void notEmptyConstructor() {
        assertNotNull(course);
    }

    @Test
    void getId() {
        assertEquals("CSE1120", course.getCourseId());
    }

    @Test
    void setId() {
        course.setCourseId("CSE11");
        assertEquals("CSE11", course.getCourseId());
    }

    @Test
    void getSize() {
        assertEquals(500, course.getCourseSize());
    }

    @Test
    void setSize() {
        course.setCourseSize(20);
        assertEquals(20, course.getCourseSize());
    }

    @Test
    void getCandidates() {
        assertEquals(0, course.getCandidateTas().size());
    }

    @Test
    void setCandidates() {
        Set<String> s = new HashSet<>();
        s.add("123");
        course.setCandidateTas(s);
        assertEquals(1, course.getCandidateTas().size());
    }

    @Test
    void setHired() {
        Set<String> s = new HashSet<>();
        s.add("123");
        course.setHiredTas(s);
        assertEquals(1, course.getHiredTas().size());
    }
}