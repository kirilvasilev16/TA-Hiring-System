package lecturer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class LecturerApplicationTest {
    //note: I'm going to keep this test commented as it does not work properly with pitest.
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
