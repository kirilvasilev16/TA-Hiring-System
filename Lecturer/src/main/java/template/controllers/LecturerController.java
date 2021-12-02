package template.controllers;

import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import template.entities.Course;
import template.entities.Lecturer;
import template.entities.Student;
import template.services.LecturerService;

@SuppressWarnings("PMD")
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

    @GetMapping("/get")
    public Lecturer getLecturer(@PathParam("netId") String netId) {
        return lecturerService.findLecturerById(netId);
    }

    @GetMapping("/courses")
    public List<Course> getOwnCourses(@PathParam("netId") String netId) {
        return lecturerService.getOwnCourses(netId);
    }

    @GetMapping("/courses/course")
    public Course getSpecificCourse(@PathParam("netId") String netId,
                                    @RequestBody Course course) {
        return lecturerService.getSpecificCourse(netId, course);
    }

    @GetMapping("/courses/course/candidateTas")
    public List<Student> getCandidateTas(@PathParam("netId") String netId,
                                         @RequestBody Course course) {
        return lecturerService.getCandidateTaList(netId, course);
    }

    @PatchMapping("/courses/course")
    public void selectTaForCourse(
            @PathParam("netId") String netId,
            @RequestBody Course course,
            @PathParam("studentId") String studentNetId) {
        lecturerService.chooseTa(netId, course, studentNetId);
    }

    @PatchMapping("/courses/addCourse")
    public Lecturer addSpecificCourse(@PathParam("netId") String netId,
                                  @RequestBody Course course) {
        return lecturerService.addSpecificCourse(netId, course);
    }
}
