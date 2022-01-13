package lecturer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LecturerApplicationTest {
//    @Test
//    void main() {
//        assertDoesNotThrow(() -> {
//            LecturerApplication.main(new String[]{});
//        });
//    }
    @Test
    void getRestTemplate() {
        LecturerApplication lecturerApplication = new LecturerApplication();
        assertNotNull(lecturerApplication.getRestTemplate());
    }

}
