package nl.tudelft.sem.student.repositories;

import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
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

    Optional<Student> findStudentByNetId(String id);

}
