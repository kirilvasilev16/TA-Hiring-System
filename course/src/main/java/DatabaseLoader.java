import entities.Course;
import org.springframework.stereotype.Service;
import repositories.CourseRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@Service
public class DatabaseLoader {

    /** The constructor for the example database loader.
     *
     */
    public DatabaseLoader(CourseRepository courseRepo) {
        Course c1 = new Course("Sample Course", new HashSet<>(), new Date());

        courseRepo.save(c1);

    }
}