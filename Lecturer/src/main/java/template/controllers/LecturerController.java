package template.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import template.entities.Course;
import template.entities.Lecturer;
import template.entities.Student;
import template.services.LecturerService;

import java.util.List;

@RestController
@RequestMapping("/lecturer")
public class LecturerController {

	private final transient LecturerService lecturerService;

	public LecturerController(LecturerService lecturerService) {
		this.lecturerService = lecturerService;
	}

	@PostMapping("/addLecturer")
	public void addLecturer(@RequestBody Lecturer lecturer) {
		lecturerService.addLecturer(lecturer);
	}

	@GetMapping("/getAll")
	public List<Lecturer> findAll() {
		return lecturerService.findAll();
	}

	@GetMapping("/{netId}")
	public Lecturer getLecturer(@PathVariable String netId) {
		return lecturerService.findLecturerById(netId);
	}

	@GetMapping("/courses/{netId}")
	public List<Course> getOwnCourses(@PathVariable String netId) {
		return lecturerService.getOwnCourses(netId);
	}

	@GetMapping("/courses/{netId}/course")
	public Course getSpecificCourse(@PathVariable String netId, @RequestBody Course course) {
		return lecturerService.getSpecificCourse(netId, course);
	}

	@GetMapping("/courses/{netId}/candidateTas")
	public List<Student> getCandidateTas(@PathVariable String netId, @RequestBody Course course) {
		return lecturerService.getCandidateTaList(netId, course);
	}

	@PatchMapping("/courses/{netId}/{studentNetId}")
	public void selectTaForACourse(@PathVariable String netId, @RequestBody Course course, @PathVariable String studentNetId) {
		lecturerService.chooseTa(netId, course, studentNetId);
	}

	@PatchMapping("/courses/{netId}/addCourse")
	public void addSpecificCourse(@PathVariable String netId, @RequestBody Course course) {
		lecturerService.addSpecificCourse(netId, course);
	}
}
