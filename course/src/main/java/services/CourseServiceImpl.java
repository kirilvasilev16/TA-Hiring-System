package services;

import entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import repositories.CourseRepository;
import services.interfaces.CourseService;

public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course findByCourseName(String courseName) {
        return courseRepository.findByCourseName(courseName);
    }

}
