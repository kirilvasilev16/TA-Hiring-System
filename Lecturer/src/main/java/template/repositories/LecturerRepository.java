package template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import template.entities.Lecturer;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
	Optional<Lecturer> findLecturerByNetId(String netId);
}
