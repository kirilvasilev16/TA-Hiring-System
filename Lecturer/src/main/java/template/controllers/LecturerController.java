package template.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import template.entities.Course;
import template.entities.Lecturer;
import template.entities.Student;
import template.repositories.LecturerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController("/lecturer")
public class LecturerController {

	private LecturerRepository lecturerRepository;
	private RestTemplate restTemplate = new RestTemplate();

	public LecturerController(LecturerRepository lecturerRepository) {
		this.lecturerRepository = lecturerRepository;
	}

	@GetMapping("/courses/{netId}")
	public List<Course> getOwnCourses(@PathVariable String netId) {
		Lecturer lecturer = lecturerRepository.findLecturerByNetId(netId);
		return lecturer.getCourses();
	}

	@GetMapping("/courses/{netId}/{courseId}")
	public Course getSpecificCourse(@PathVariable String netId, @PathVariable String courseId) {
		Course course = restTemplate.getForObject("http://localhost:8080/course/" + courseId, Course.class);
		Lecturer lecturer = lecturerRepository.findLecturerByNetId(netId);
		if (lecturer.getCourses().contains(course)) {
			return course;
		}
		else throw new EntityNotFoundException("Course is not taught by lecturer");
	}

	private LecturerRepository getLecturerRepository() {
		return lecturerRepository;
	}

	private void setLecturerRepository(LecturerRepository lecturerRepository) {
		this.lecturerRepository = lecturerRepository;
	}

	private RestTemplate getRestTemplate() {
		return restTemplate;
	}

	private void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

//	@PatchMapping("/courses/{netId}/{courseId}/{studentNetId}")
//	public void selectTaForACourse(@PathVariable String netId, @PathVariable String courseId, @PathVariable String studentNetId) {
//		Course course = getSpecificCourse(netId, courseId);
//		Student student = restTemplate.post("http://localhost:8080/course/" + courseId + "/addTA/" + studentNetId);
//		course.getHiredTa().add(student);
//	}
}
