package course.services.interfaces;

import course.entities.Course;

public interface CourseService {
    Course findByCourseName(String courseName);

}
