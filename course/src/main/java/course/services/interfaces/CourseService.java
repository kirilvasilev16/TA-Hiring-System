package course.services.interfaces;

import course.entities.Course;
import org.springframework.stereotype.Service;

public interface CourseService {
    Course findByCourseName(String courseName);

}
