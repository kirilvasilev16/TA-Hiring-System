package course.services;

import course.entities.Course;
import course.repositories.CourseRepository;
import course.services.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    private final transient CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course findByName(String name) {
        return courseRepository.findByName(name);
    }

    @Override
    public Course findByCourseID(String id) {
        return courseRepository.findByCourseID(id);
    }

    @Override
    public void save(Course c) {
        courseRepository.save(c);
    }

//    @Override
//    public void updateCourseSize(Integer courseSize) {
//        courseRepository.updateCourseSize(courseSize);
//    }

}
