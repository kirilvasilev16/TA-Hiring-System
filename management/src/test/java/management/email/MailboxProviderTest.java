package management.email;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class MailboxProviderTest {

    private transient EmailSender emailSender;

    @BeforeEach
    void setup() {
        emailSender = new MailboxProvider();
    }

    @Test
    void constructor() {
        assertNotNull(emailSender);
    }

    @Test
    void contractSending() {
        final PrintStream standardOut = System.out;
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outputStreamCaptor));

        emailSender.sendEmail("email", "CSE1200","kvasilev", 70);
        String output = "Contract:\n"
                + "Student: kvasilev\n"
                + "Course: CSE1200\n"
                + "Hours in contract: 70.0";
        assertEquals(output, outputStreamCaptor.toString().trim());

        System.setOut(standardOut);
    }
}