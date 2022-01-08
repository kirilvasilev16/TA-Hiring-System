package course;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class CourseApplicationTest {

    @Test
    void main() {
        assertDoesNotThrow(() -> {
            CourseApplication.main(new String[]{});
        });
    }
}

