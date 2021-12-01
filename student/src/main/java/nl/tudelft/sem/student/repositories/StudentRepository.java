package nl.tudelft.sem.student.repositories;

import nl.tudelft.sem.student.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * The interface Student repository.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    @Modifying
    @Query("UPDATE Student s SET s.candidateCourses = :candidate WHERE s.netId = :netId")
    void updateCandidateCourses(@Param("netId") String netId, @Param("candidate") Set<String> candidate);

    @Modifying
    @Query("UPDATE Student s SET s.taCourses = :ta WHERE s.netId = :netId")
    void updateTaCourses(@Param("netId") String netId, @Param("ta") Set<String> ta);
}
