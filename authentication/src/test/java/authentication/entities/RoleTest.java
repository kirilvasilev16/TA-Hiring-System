package authentication.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoleTest {
    private transient Role role;
    private transient String name = "name";

    @BeforeEach
    void setUp() {
        role = new Role(name);
    }

    @Test
    void constructor() {
        assertNotNull(role);
    }

    @Test
    void getName() {
        assertEquals(name, role.getName());
    }

    @Test
    void setName() {
        role.setName("myName");
        assertEquals("myName", role.getName());
    }

}
