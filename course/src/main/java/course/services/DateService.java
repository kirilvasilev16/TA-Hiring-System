package course.services;

import java.time.LocalDateTime;
import java.util.Calendar;
import org.springframework.stereotype.Service;

@Service
public class DateService {

    public DateService() {

    }

    /**
     * Get today's date.
     *
     * @return LocalDateTime object
     */
    public LocalDateTime getTodayDate() {
        return LocalDateTime.now();
    }
}
