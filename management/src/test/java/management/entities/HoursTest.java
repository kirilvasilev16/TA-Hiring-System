package management.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HoursTest {

    private transient Hours hours;
    @BeforeEach
    void setUp() {
        hours = new Hours("CSE1200", "kvasilev", 120.0f);
    }

    @Test
    void getCourseId() {
        assertEquals("CSE1200", hours.getCourseId());
    }

    @Test
    void setCourseId() {
        hours.setCourseId("CSE1300");
        assertEquals("CSE1300", hours.getCourseId());
    }

    @Test
    void getStudentId() {
        assertEquals("kvasilev", hours.getStudentId());
    }

    @Test
    void setStudentId() {
        hours.setStudentId("aatanasov");
        assertEquals("aatanasov", hours.getStudentId());
    }

    @Test
    void getAmountOfHours() {
        assertEquals(120.0f, hours.getAmountOfHours());
    }

    @Test
    void setAmountOfHours() {
        hours.setAmountOfHours(140.0f);
        assertEquals(140.0f, hours.getAmountOfHours());
    }
}