package course.repositories;


import course.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByName(String name);
    Course findByCourseID(String name);

//    @Modifying
//    @Query("UPDATE Course c SET c.courseSize = :courseSize WHERE q.courseID = :courseID")
//    void updateCourseSize(Integer courseSize);
}
