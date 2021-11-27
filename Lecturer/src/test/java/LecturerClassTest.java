import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import template.entities.Course;
import template.entities.Lecturer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LecturerClassTest {
    private transient Lecturer lecturer;

    @BeforeEach
    void setUp() {
        lecturer = new Lecturer("netId", "name", "password", "email", new ArrayList<>());
    }

    @Test
    void constructor() {
        assertNotNull(lecturer);
    }

    @Test
    void getId() {
        assertEquals("netId", lecturer.getNetId());
    }

    @Test
    void setId() {
        lecturer.setNetId("myId");
        assertEquals("myId", lecturer.getNetId());
    }

    @Test
    void getName() {
        assertEquals("name", lecturer.getName());
    }

    @Test
    void setName() {
        lecturer.setName("myName");
        assertEquals("myName", lecturer.getName());
    }

    @Test
    void getPassword() {
        assertEquals("password", lecturer.getPassword());
    }

    @Test
    void setPassword() {
        lecturer.setPassword("myPassword");
        assertEquals("myPassword", lecturer.getPassword());
    }

    @Test
    void getEmail() {
        assertEquals("email", lecturer.getEmail());
    }

    @Test
    void setEmail() {
        lecturer.setEmail("myEmail");
        assertEquals("myEmail", lecturer.getEmail());
    }

    @Test
    void getCourses() {
        assertEquals(0, lecturer.getCourses().size());
    }

    @Test
    void setCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course());
        lecturer.setCourses(courses);
        assertEquals(courses, lecturer.getCourses());
    }

    @Test
    void equalsTest() {
        Lecturer newLecturer = new Lecturer("netId", "name", "password", "email", new ArrayList<>());
        assertEquals(lecturer, newLecturer);
    }

    @Test
    void equalsNull() {
        assertNotEquals(lecturer, null);
    }

    @Test
    void equalsSame() {
        assertEquals(lecturer, lecturer);
    }

    @Test
    void equalsNot() {
        Lecturer newLecturer = new Lecturer();
        assertNotEquals(lecturer, newLecturer);
    }

    @Test
    void hashCodeTest() {
        Lecturer newLecturer = new Lecturer("netId", "name", "password", "email", new ArrayList<>());
        System.out.println(lecturer.getName());
        assertEquals(lecturer.hashCode(), newLecturer.hashCode());
    }
}
