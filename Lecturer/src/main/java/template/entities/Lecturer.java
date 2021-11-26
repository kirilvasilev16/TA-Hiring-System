package template.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class Lecturer {
	@Id
	private String netId;
	private String name;
	private String password;
	private String email;
	@ManyToMany
	List<Course> courses;

	public Lecturer(String netId, String name, String password, String email, List<Course> courses) {
		this.netId = netId;
		this.name = name;
		this.password = password;
		this.email = email;
		this.courses = courses;
	}

	public Lecturer() {
	}

	public String getNetId() {
		return netId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	public String getPassword() {
		return password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Lecturer lecturer = (Lecturer) o;
		return Objects.equals(netId, lecturer.netId) && Objects.equals(name, lecturer.name) && Objects.equals(password, lecturer.password) && Objects.equals(email, lecturer.email) && Objects.equals(courses, lecturer.courses);
	}

	@Override
	public int hashCode() {
		return Objects.hash(netId, name, password, email, courses);
	}
}
