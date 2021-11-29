package management.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManagementTest {

    private transient Management management;
    private final transient String courseId = "CSE1200";
    private final transient String netId = "kvasilev";

    @BeforeEach
    void setUp() {
        management = new Management(courseId, netId, 120);
    }

    @Test
    public void constructorEmpty() {
        management = new Management();
        assertNotNull(management);
    }

    @Test
    public void constructor() {
        assertNotNull(management);
    }

    @Test
    void getId() {
        assertEquals(0, management.getId());
    }

    @Test
    void setId() {
        management.setId(1);
        assertEquals(1, management.getId());
    }

    @Test
    void getCourseId() {
        assertEquals(courseId, management.getCourseId());
    }

    @Test
    void setCourseId() {
        management.setCourseId("CSE1300");
        assertEquals("CSE1300", management.getCourseId());
    }

    @Test
    void getStudentId() {
        assertEquals(netId, management.getStudentId());
    }

    @Test
    void setStudentId() {
        management.setStudentId("aatanasov");
        assertEquals("aatanasov", management.getStudentId());
    }

    @Test
    void getAmountOfHours() {
        assertEquals(120, management.getAmountOfHours());
    }

    @Test
    void setAmountOfHours() {
        management.setAmountOfHours(150);
        assertEquals(150, management.getAmountOfHours());
    }

    @Test
    void getApprovedHours() {
        assertEquals(0, management.getApprovedHours());
    }

    @Test
    void setApprovedHours() {
        management.setApprovedHours(50);
        assertEquals(50, management.getApprovedHours());
    }

    @Test
    void getDeclaredHours() {
        assertEquals(0, management.getDeclaredHours());
    }

    @Test
    void setDeclaredHours() {
        management.setDeclaredHours(10);
        assertEquals(10, management.getDeclaredHours());
    }

    @Test
    void getRating() {
        assertEquals(0, management.getRating());
    }

    @Test
    void setRating() {
        management.setRating(5);
        assertEquals(5, management.getRating());
    }

    @Test
    void equalsTest() {
        Management management1 = new Management(courseId, netId, 120);
        assertEquals(management, management1);
    }

    @Test
    void equalsNull() {
        assertNotEquals(management, null);
    }

    @Test
    void equalsSame() {
        assertEquals(management, management);
    }

    @Test
    void equalsNot() {
        Management management1 = new Management(courseId, "aatanasov", 120);
        assertNotEquals(management, management1);
    }

    @Test
    void hashCodeTest() {
        Management management1 = new Management(courseId, netId, 120);
        assertEquals(management.hashCode(), management1.hashCode());
    }
}
