package course;

import course.entities.Course;
import course.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashSet;

@Service
public class DatabaseLoader {

    /**
     * The constructor for the example database loader.
     */
    public DatabaseLoader(CourseRepository courseRepo) {
        Course c1 = new Course("Sample", new HashSet<>(), new Date());
        System.out.println("Adding");
        courseRepo.save(c1);

    }
}