package lecturer.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class StudentClassTest {

    @Test
    void constructor() {
        Student student = new Student("1");
        assertNotNull(student);
    }

    @Test
    void id() {
        Student student = new Student("1");
        student.setNetId("2");
        assertEquals("2", student.getNetId());
    }
}
