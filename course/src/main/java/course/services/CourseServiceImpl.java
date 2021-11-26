package course.services;

import course.entities.Course;
import course.repositories.CourseRepository;
import course.services.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
