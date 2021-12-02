package course;

import course.entities.Course;
import course.entities.Lecturer;
import course.entities.Student;
import course.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Service
public class DatabaseLoader {

    /**
     * The constructor for the example database loader.
     */
    public DatabaseLoader(CourseRepository courseRepo) {
        Course c1 = new Course("CSE5000", "Sample", 5, new HashSet<String>(), new Date());
        System.out.println("Adding");

        courseRepo.save(c1);

    }
}