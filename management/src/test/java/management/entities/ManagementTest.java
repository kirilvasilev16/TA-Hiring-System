package management.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManagementTest {

    private transient Management management;

    @BeforeEach
    void setUp() {
        management = new Management(100, 200, 120);
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
        assertEquals(100, management.getCourseId());
    }

    @Test
    void setCourseId() {
        management.setCourseId(240);
        assertEquals(240, management.getCourseId());
    }

    @Test
    void getStudentId() {
        assertEquals(200, management.getStudentId());
    }

    @Test
    void setStudentId() {
        management.setStudentId(212);
        assertEquals(212, management.getStudentId());
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
        Management management1 = new Management(100, 200, 120);
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
        Management management1 = new Management(100, 210, 120);
        assertNotEquals(management, management1);
    }

    @Test
    void hashCodeTest() {
        Management management1 = new Management(100, 200, 120);
        assertEquals(management.hashCode(), management1.hashCode());
    }
}
