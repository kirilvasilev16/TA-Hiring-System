package course.services;

import java.util.Calendar;
import org.springframework.stereotype.Service;

@Service
public class DateService {

    public DateService() {

    }

    /**
     * Get today's date.
     *
     * @return Calendar object
     */
    public Calendar getTodayDate() {
        return Calendar.getInstance();
    }
}
