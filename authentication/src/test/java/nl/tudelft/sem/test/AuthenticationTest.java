package nl.tudelft.sem.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import authentication.entities.Authentication;
import authentication.entities.Role;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthenticationTest {
    private transient Authentication auth;
    private transient String netId = "net@id.nl";
    private transient String name = "name";
    private transient String password = "password";

    @BeforeEach
    void setUp() {
        auth = new Authentication(netId, password, name, new ArrayList<>());
    }

    @Test
    void constructor() {
        assertNotNull(auth);
    }

    @Test
    void getPassword() {
        assertEquals(password, auth.getPassword());
    }

    @Test
    void setPassword() {
        auth.setPassword("myPassword");
        assertEquals("myPassword", auth.getPassword());
    }

    @Test
    void getRoles() {
        assertEquals(0, auth.getRoles().size());
    }

    @Test
    void setRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_ta"));
        auth.setRoles(roles);
        assertEquals(roles, auth.getRoles());
    }

    @Test
    void getNetId() {
        assertEquals(netId, auth.getNetId());
    }

    @Test
    void setNetId() {
        auth.setNetId("myId");
        assertEquals("myId", auth.getNetId());
    }

    @Test
    void getName() {
        assertEquals(name, auth.getName());
    }

    @Test
    void setName() {
        auth.setName("myName");
        assertEquals("myName", auth.getName());
    }
}