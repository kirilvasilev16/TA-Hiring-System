package lecturer.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import lecturer.entities.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Optional<Lecturer> findLecturerByNetId(String netId);
}
