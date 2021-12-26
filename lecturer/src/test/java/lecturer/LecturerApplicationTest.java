package lecturer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class LecturerApplicationTest {
    @Test
    void main() {
        assertDoesNotThrow(() -> {
            LecturerApplication.main(new String[]{});
        });
    }
}
