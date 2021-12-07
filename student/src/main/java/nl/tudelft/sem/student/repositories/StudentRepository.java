package nl.tudelft.sem.student.repositories;

import java.util.Optional;
import java.util.Set;
import nl.tudelft.sem.student.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The interface Student repository.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    /**
     * Update candidate courses.
     *
     * @param netId     the net id
     * @param candidate the updated set of candidate courses
     */
    @Modifying
    @Query("UPDATE Student s SET s.candidateCourses = :candidate WHERE s.netId = :netId")
    void updateCandidateCourses(@Param("netId") String netId,
                                @Param("candidate") Set<String> candidate);

    /**
     * Update ta courses.
     *
     * @param netId the net id
     * @param ta    the updated set of TA courses
     */
    @Modifying
    @Query("UPDATE Student s SET s.taCourses = :ta WHERE s.netId = :netId")
    void updateTaCourses(@Param("netId") String netId,
                         @Param("ta") Set<String> ta);

    Optional<Student> findStudentByNetId(String id);
}
