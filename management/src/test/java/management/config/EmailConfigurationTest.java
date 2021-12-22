package management.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailConfigurationTest {

    private transient EmailConfiguration emailConfiguration;

    @BeforeEach
    void setup() {
        emailConfiguration = new EmailConfiguration();
    }

    @Test
    void constructor() {
        assertNotNull(emailConfiguration);
    }

    @Test
    void testCreateEmailSender() {
        assertNotNull(emailConfiguration.createEmailSender());
    }
}