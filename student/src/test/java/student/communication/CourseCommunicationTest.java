package student.communication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseCommunicationTest {

    private transient CourseCommunication courseCommunication;
    private transient Set<String> set;

    @BeforeEach
    void setUp() {
        courseCommunication = new CourseCommunication();
        set = new HashSet<>();
    }

    @Test
    void test() {
        assertDoesNotThrow(() ->
                courseCommunication.checkApplyRequirement("ohageman",
                "CSE2115-2022", set));
        assertFalse(courseCommunication.checkApplyRequirement("ohageman",
                "CSE2115-2022", set));
    }

}
