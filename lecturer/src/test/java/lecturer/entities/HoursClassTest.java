package lecturer.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class HoursClassTest {

    @Test
    void constructor() {
        Hours hours = new Hours("1", "1", 1.0f);
        assertNotNull(hours);
    }

    @Test
    void courseId() {
        Hours hours = new Hours("1", "1", 1.0f);
        hours.setCourseId("2");
        assertEquals("2", hours.getCourseId());
    }

    @Test
    void studentId() {
        Hours hours = new Hours("1", "1", 1.0f);
        hours.setStudentId("2");
        assertEquals("2", hours.getStudentId());
    }

    @Test
    void hours() {
        Hours hours = new Hours("1", "1", 1.0f);
        hours.setAmountOfHours(5);
        assertEquals(5, hours.getAmountOfHours());
    }
}
