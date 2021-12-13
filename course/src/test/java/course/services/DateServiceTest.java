package course.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
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
        Calendar expect = Calendar.getInstance();

        assertEquals(expect, dateService.getTodayDate());
    }
}