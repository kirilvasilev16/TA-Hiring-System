package student.repositories;

import java.util.Optional;

import student.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Student repository.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findStudentByNetId(String id);

}
