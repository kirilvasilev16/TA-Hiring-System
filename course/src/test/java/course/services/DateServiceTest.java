package course.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DateServiceTest {

    private transient DateService dateService;

    @BeforeEach
    void setUp() {
        dateService = new DateService();
    }

    @Test
    void getTodayDate() {
        LocalDateTime expect = LocalDateTime.now();
        LocalDateTime actual = dateService.getTodayDate();

        assertEquals(expect.getDayOfYear(), actual.getDayOfYear());
    }
}