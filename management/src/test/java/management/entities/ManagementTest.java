package management.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ManagementTest {

    private transient Management management;
    private static final transient String courseId = "CSE1200";
    private static final transient String netId = "kvasilev";

    @BeforeEach
    void setUp() {
        management = new Management(courseId, netId, 120);
    }

    @Test
    public void constructorEmpty() {
        management = new Management();
        assertNotNull(management);
    }

    @Test
    public void constructor() {
        assertNotNull(management);
    }

    @Test
    void getId() {
        assertEquals(0, management.getId());
    }

    @Test
    void setId() {
        management.setId(1);
        assertEquals(1, management.getId());
    }

    @Test
    void getCourseId() {
        assertEquals(courseId, management.getCourseId());
    }

    @Test
    void setCourseId() {
        management.setCourseId("CSE1300");
        assertEquals("CSE1300", management.getCourseId());
    }

    @Test
    void getStudentId() {
        assertEquals(netId, management.getStudentId());
    }

    @Test
    void setStudentId() {
        management.setStudentId("aatanasov");
        assertEquals("aatanasov", management.getStudentId());
    }

    @Test
    void getAmountOfHours() {
        assertEquals(120, management.getAmountOfHours());
    }

    @Test
    void setAmountOfHours() {
        management.setAmountOfHours(150);
        assertEquals(150, management.getAmountOfHours());
    }

    @Test
    void getApprovedHours() {
        assertEquals(0, management.getApprovedHours());
    }

    @Test
    void setApprovedHours() {
        management.setApprovedHours(50);
        assertEquals(50, management.getApprovedHours());
    }

    @Test
    void getDeclaredHours() {
        assertEquals(0, management.getDeclaredHours());
    }

    @Test
    void setDeclaredHours() {
        management.setDeclaredHours(10);
        assertEquals(10, management.getDeclaredHours());
    }

    @Test
    void getRating() {
        assertEquals(-1, management.getRating());
    }

    @Test
    void setRating() {
        management.setRating(5);
        assertEquals(5, management.getRating());
    }

    @ParameterizedTest
    @MethodSource("generatorEquals")
    void equalsNot(Object management1) {
        assertNotEquals(management, management1);
    }

    private static Stream<Arguments> generatorEquals() {

        Management wrongId = new Management(courseId, netId, 120);
        wrongId.setId(4L);
        Management wrongRating = new Management(courseId, netId, 120);
        wrongRating.setRating(4.0f);
        Management wrongDeclaredHours = new Management(courseId, netId, 120);
        wrongDeclaredHours.setDeclaredHours(20);
        Management wrongApprovedHours = new Management(courseId, netId, 120);
        wrongApprovedHours.setApprovedHours(30);
        Management nullManagement = null;
        return Stream.of(
                Arguments.of(nullManagement),
                Arguments.of(wrongId),
                Arguments.of(wrongRating),
                Arguments.of(wrongDeclaredHours),
                Arguments.of(wrongApprovedHours),
                Arguments.of(3),
                Arguments.of(new Management(courseId + "C", netId, 120)),
                Arguments.of(new Management(courseId, netId + "C", 120)),
                Arguments.of(new Management(courseId, netId, 123))
        );
    }

    @Test
    void equalsManagement() {
        Management management1 = new Management(courseId, netId, 120);
        assertEquals(management, management1);
    }

    @Test
    void equalsSame() {
        assertEquals(management, management);
    }

    @Test
    void hashCodeTest() {
        Management management1 = new Management(courseId, netId, 120);
        assertEquals(management.hashCode(), management1.hashCode());
    }

    @Test
    void hashCodeTestFail() {
        Management management1 = new Management(courseId, netId, 120);
        management.setId(11);
        assertNotEquals(management.hashCode(), management1.hashCode());
    }
}
