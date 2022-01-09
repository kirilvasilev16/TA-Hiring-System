package course;

import course.entities.Course;
import course.repositories.CourseRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
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
        Course c1 = new Course("CSE5000", "Sample", 5, new HashSet<String>(), date, 2);
        courseRepo.save(c1);

        Course c2 = new Course("CSE2115-2022",
                "SEM",
                500,
                new HashSet<>(Arrays.asList("azaidman", "toverklift", "gmigut", "mrhug")),
                LocalDateTime.of(LocalDate.of(2022, 11, 1), LocalTime.NOON),
                2);
        c2.setCandidateTas(new HashSet<>(Arrays.asList("esozen",
                "chengmouyeh",
                "akalandadze",
                "ohageman"
        )));
        c2.setHiredTas(new HashSet<>(Arrays.asList("kvasilev", "etrinh")));
        courseRepo.save(c2);

        Course c3 = new Course("CSE1105-2022",
                "OOPP",
                500,
                new HashSet<>(Arrays.asList("gmigut", "mrhug")),
                LocalDateTime.of(LocalDate.of(2022, 3, 1), LocalTime.NOON),
                2);
        c3.setCandidateTas(new HashSet<>(Arrays.asList("kvasilev", "etrinh")));
        c3.setHiredTas(new HashSet<>(Arrays.asList("esozen",
                "chengmouyeh",
                "akalandadze",
                "ohageman")));
        courseRepo.save(c3);

        Course c4 = new Course("CSE1400-2022",
                "CO",
                500,
                new HashSet<>(Arrays.asList("ovisser", "taerts")),
                LocalDateTime.of(LocalDate.of(2022, 3, 1), LocalTime.NOON),
                2);
        c4.setCandidateTas(new HashSet<>(Arrays.asList("kvasilev",
                "etrinh",
                "akalandadze",
                "ohageman")));
        c4.setHiredTas(new HashSet<>(Arrays.asList("esozen", "chengmouyeh")));
        courseRepo.save(c4);

    }
}