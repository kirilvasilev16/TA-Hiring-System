package course.services;

import course.entities.Course;
import course.repositories.CourseRepository;
import course.services.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    private final transient CourseRepository courseRepository;

    /**
     * Course Service Impl Constructor.
     *
     * @param courseRepository CourseRepository object
     */
    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Query and find Course by course name.
     *
     * @param name String course name
     * @return Course object if found, else null
     */
    @Override
    public Course findByName(String name) {
        return courseRepository.findByName(name);
    }

    /**
     * Query and find Course by courseID.
     *
     * @param id String courseID
     * @return Course object if found, else null
     */
    @Override
    public Course findByCourseId(String id) {
        return courseRepository.findByCourseId(id);
    }

    /**
     * Persist Course object to database.
     *
     * @param c Course object
     */
    @Override
    public void save(Course c) {
        courseRepository.save(c);
    }

    /**
     * Update course size of course.
     *
     * @param courseSize Integer course size
     */
    @Override
    public void updateCourseSize(Integer courseSize) {
        courseRepository.updateCourseSize(courseSize);
    }

}
