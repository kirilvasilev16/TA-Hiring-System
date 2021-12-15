package course;

import course.entities.Course;
import course.repositories.CourseRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashSet;
import org.springframework.stereotype.Service;


@Service
public class DatabaseLoader {

    /**
     * The constructor for the example database loader.
     */
    public DatabaseLoader(CourseRepository courseRepo) {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(2021, 11, 7), LocalTime.NOON);
        Course c1 = new Course("CSE5000", "Sample", 5, new HashSet<String>(), date);
        //System.out.println("Adding");

        courseRepo.save(c1);

    }
}