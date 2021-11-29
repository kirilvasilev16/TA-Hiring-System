package authentication.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class AuthTest {
    @Test
    public void testExample() {
        Authentication exampleTest = new Authentication("net@id.com",
                "password", "net-id", new ArrayList());
        assertEquals("net@id.com", exampleTest.getNetId());
    }
}
