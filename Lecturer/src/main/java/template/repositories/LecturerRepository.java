package template.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import template.entities.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
	Lecturer findLecturerByNetId(String netId);
}
