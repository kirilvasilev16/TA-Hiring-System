package course.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ManagementTest {

    private transient Management management;

    @BeforeEach
    void setUp() {
        management = new Management();
    }


    @Test
    void setAndGetManagementId() {
        String id = "id";
        management.setManagementId(id);

        assertEquals(id, management.getManagementId());
    }

    @Test
    void setAndGetRating() {
        float rating = 1.f;
        management.setRating(rating);

        assertEquals(rating, management.getRating());
    }
}