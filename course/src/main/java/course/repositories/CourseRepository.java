package course.repositories;


import course.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByName(String name);

    Course findByCourseId(String name);

    @Modifying
    @Query("UPDATE Course c SET c.courseSize = :courseSize WHERE c.courseId = :courseID")
    void updateCourseSize(Integer courseSize);
}
