package template.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import template.entities.Course;
import template.entities.Lecturer;
import template.entities.Student;
import template.services.LecturerService;

import javax.websocket.server.PathParam;

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
    public Lecturer getLecturer(@PathVariable @PathParam("id") String netId) {
        return lecturerService.findLecturerById(netId);
    }

    @GetMapping("/courses/{netId}")
    public List<Course> getOwnCourses(@PathVariable @PathParam("id") String netId) {
        return lecturerService.getOwnCourses(netId);
    }

    @GetMapping("/courses/{netId}/course")
    public Course getSpecificCourse(@PathVariable @PathParam("id") String netId, @RequestBody Course course) {
        return lecturerService.getSpecificCourse(netId, course);
    }

    @GetMapping("/courses/{netId}/course/candidateTas")
    public List<Student> getCandidateTas(@PathVariable @PathParam("id") String netId, @RequestBody Course course) {
        return lecturerService.getCandidateTaList(netId, course);
    }

    @PatchMapping("/courses/{netId}/course/{studentNetId}")
    public void selectTaForCourse(
            @PathVariable @PathParam("id") String netId,
            @RequestBody Course course,
            @PathVariable @PathParam("studentId") String studentNetId) {
        lecturerService.chooseTa(netId, course, studentNetId);
    }

    @PatchMapping("/courses/{netId}/addCourse")
    public void addSpecificCourse(@PathVariable @PathParam("id") String netId, @RequestBody Course course) {
        lecturerService.addSpecificCourse(netId, course);
    }
}
