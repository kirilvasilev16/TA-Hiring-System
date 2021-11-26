package template.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Course {
	private Long id;
	@ManyToMany
	private List<Student> hiredTa;
	public void setId(Long id) {
		this.id = id;
	}

	@Id
	public Long getId() {
		return id;
	}

	public List<Student> getHiredTa() {
		return hiredTa;
	}
}
