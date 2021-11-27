package management.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ManagementTest {

    @Test
    public void testExample() {
        Management management = new Management(100, 200, 120);
        assertEquals(0, management.getRating());
        management.setRating(3);
        assertEquals(3, management.getRating());
    }
}
