package course.services;

import course.entities.Course;
import course.repositories.CourseRepository;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CourseService {
    private final transient CourseRepository courseRepository;

    /**
     * Course Service Impl Constructor.
     *
     * @param courseRepository CourseRepository object
     */
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Query and find Course by courseID.
     *
     * @param id String courseID
     * @return Course object if found, else null
     */
    public Course findByCourseId(String id) {
        return courseRepository.findByCourseId(id);
    }

    /**
     * Persist Course object to database.
     *
     * @param c Course object
     */
    public void save(Course c) {
        courseRepository.save(c);
    }

    /**
     * Update course size of course.
     *
     * @param courseSize Integer course size
     */
    public void updateCourseSize(String courseId, Integer courseSize) {
        courseRepository.updateCourseSize(courseId, courseSize);
    }


    //    /**
    //     * Update candidate Tas set of course.
    //     *
    //     * @param courseId     String courseId
    //     * @param candidateTas Set of Strings where strings are studentIds
    //     */
    //
    //    public void updateCandidateTas(String courseId, Set<String> candidateTas) {
    //        courseRepository.updateCandidateTas(courseId, candidateTas);
    //    }
    //
    //    /**
    //     * Update lecturer set of course.
    //     *
    //     * @param courseId    String courseId
    //     * @param lecturerSet Set of Strings where strings are lecturerIds
    //     */
    //    public void updateLecturerSet(String courseId, Set<String> lecturerSet) {
    //        courseRepository.updateLecturerSet(courseId, lecturerSet);
    //    }
    //
    //    /**
    //     * Update hired tas set of course.
    //     *
    //     * @param courseId String courseId
    //     * @param hireTas  Set of Strings where strings are studentIds
    //     */
    //    public void updateHireTas(String courseId, Set<String> hireTas) {
    //        courseRepository.updateHireTas(courseId, hireTas);
    //    }


}
