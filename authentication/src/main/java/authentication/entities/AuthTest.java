package authentication.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AuthTest {
        @Test
        public void testExample() {
            Authentication exampleTest = new Authentication("net@id.com","net-id");
            assertEquals("net@id.com", exampleTest.getNetId());
        }
}
