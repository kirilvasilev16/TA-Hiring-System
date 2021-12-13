package course.repositories;


import course.entities.Course;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@SuppressWarnings("PMD")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseId(String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Course c SET c.courseSize = :courseSize WHERE c.courseId = :courseId")
    void updateCourseSize(@Param("courseId") String courseId,
                          @Param("courseSize") Integer courseSize);

    /*
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Course c SET c.candidateTas = :candidateTas WHERE c.courseId = :courseId")
    void updateCandidateTas(@Param("courseId") String courseId,
                            @Param("candidateTas") Set<String> candidateTas);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Course c SET c.lecturerSet = :lecturerSet WHERE c.courseId = :courseId")
    void updateLecturerSet(@Param("courseId") String courseId,
                           @Param("lecturerSet") Set<String> lecturerSet);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Course c SET c.hiredTas = :hiredTas where c.courseId = :courseId")
    void updateHireTas(@Param("courseId") String courseId,
                       @Param("hiredTas") Set<String> hiredTas);
    */

}
