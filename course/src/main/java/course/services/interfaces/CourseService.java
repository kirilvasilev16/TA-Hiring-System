package course.services.interfaces;

import course.entities.Course;

public interface CourseService {
    Course findByName(String name);
    Course findByCourseID(String id);

    void save(Course c);

    void updateCourseSize(Integer courseSize);
}
