package student.communication;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ManagementCommunicationTest {

    private transient ManagementCommunication managementCommunication;
    private transient String json;

    @BeforeEach
    void setUp() {
        managementCommunication = new ManagementCommunication();
        json = "[{\n" +
                "    \"courseId\" : \"CSE2115-2022\",\n" +
                "    \"studentId\" : \"kvasilev\",\n" +
                "    \"hours\" : 20.0\n" +
                "}]";
    }

    @Test
    void test() {
        assertDoesNotThrow(() ->
                managementCommunication.declareHours(json));
        assertFalse(managementCommunication.declareHours(json));
    }
}
