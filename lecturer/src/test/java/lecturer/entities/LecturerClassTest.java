package lecturer.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LecturerClassTest {
    private transient Lecturer lecturer;
    private transient String netId = "netId";
    private transient String name = "name";
    private transient String password = "password";
    private transient String email = "email";

    @BeforeEach
    void setUp() {
        lecturer = new Lecturer(netId, name, email, new ArrayList<>());
    }

    @Test
    void constructor() {
        assertNotNull(lecturer);
    }

    @Test
    void getId() {
        assertEquals(netId, lecturer.getNetId());
    }

    @Test
    void setId() {
        lecturer.setNetId("myId");
        assertEquals("myId", lecturer.getNetId());
    }

    @Test
    void getName() {
        assertEquals(name, lecturer.getName());
    }

    @Test
    void setName() {
        lecturer.setName("myName");
        assertEquals("myName", lecturer.getName());
    }

    @Test
    void getEmail() {
        assertEquals(email, lecturer.getEmail());
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
        List<String> courses = new ArrayList<>();
        courses.add("cse");
        lecturer.setCourses(courses);
        assertEquals(courses, lecturer.getCourses());
    }

    @Test
    void equalsTest() {
        Lecturer newLecturer = new Lecturer(netId, name, email, new ArrayList<>());
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
        Lecturer newLecturer = new Lecturer(netId, name, email, new ArrayList<>());
        System.out.println(lecturer.getName());
        assertEquals(lecturer.hashCode(), newLecturer.hashCode());
    }
}
