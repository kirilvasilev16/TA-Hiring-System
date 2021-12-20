package course;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CourseApplicationTest {

    @Test
    void main() {
        assertDoesNotThrow(() -> {
            CourseApplication.main(new String[]{});
        });
    }
}

