package management.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HoursTest {

    private transient Hours hours;
    private static final transient String courseId = "CSE1200";
    private static final transient String netId = "kvasilev";

    @BeforeEach
    void setUp() {
        hours = new Hours(courseId, netId, 120.0f);
    }

    @Test
    void getCourseId() {
        assertEquals(courseId, hours.getCourseId());
    }

    @Test
    void setCourseId() {
        hours.setCourseId("CSE1300");
        assertEquals("CSE1300", hours.getCourseId());
    }

    @Test
    void getStudentId() {
        assertEquals(netId, hours.getStudentId());
    }

    @Test
    void setStudentId() {
        hours.setStudentId("aatanasov");
        assertEquals("aatanasov", hours.getStudentId());
    }

    @Test
    void getAmountOfHours() {
        assertEquals(120.0f, hours.getAmountOfHours());
    }

    @Test
    void setAmountOfHours() {
        hours.setAmountOfHours(140.0f);
        assertEquals(140.0f, hours.getAmountOfHours());
    }

    @ParameterizedTest
    @MethodSource("generatorEquals")
    void equalsNot(Object hours1) {
        assertNotEquals(hours, hours1);
    }

    private static Stream<Arguments> generatorEquals() {
        Hours nullHours = null;
        return Stream.of(
                Arguments.of(nullHours),
                Arguments.of(3),
                Arguments.of(new Hours(courseId + "C", netId, 120)),
                Arguments.of(new Hours(courseId, netId + "C", 120)),
                Arguments.of(new Hours(courseId, netId, 123))
        );
    }

    @Test
    void equalsSame() {
        assertEquals(hours, hours);
    }

    @Test
    void equalsHours() {
        Hours hours1 = new Hours(courseId, netId, 120);
        assertEquals(hours, hours1);
    }

    @Test
    void hashCodeTest() {
        Hours hours1 = new Hours(courseId, netId, 120);
        assertEquals(hours.hashCode(), hours1.hashCode());
    }

    @Test
    void hashCodeTestFail() {
        Hours hours1 = new Hours(courseId, netId, 130);
        assertNotEquals(hours.hashCode(), hours1.hashCode());
    }
}