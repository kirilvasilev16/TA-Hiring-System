package nl.tudelft.sem.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import authentication.entities.Authentication;
import authentication.entities.Role;
import org.junit.jupiter.api.Test;

public class AuthTest {
    @Test
    public void testExample() {
        Authentication exampleTest = new Authentication("net@id.com",
                "password", "net-id", new ArrayList<Role>());
        assertEquals("net@id.com", exampleTest.getNetId());
    }
}
