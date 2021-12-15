package lecturer.controllers;

import java.util.List;
import javax.websocket.server.PathParam;

import lecturer.entities.Contract;
import lecturer.entities.Course;
import lecturer.entities.Lecturer;
import lecturer.entities.Student;
import lecturer.services.LecturerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/courses/getOwnCourses")
    public List<String> getOwnCourses(@PathParam("netId") String netId) {
        return lecturerService.getOwnCourses(netId);
    }

    @GetMapping("/courses/getSpecificCourse")
    public Course getSpecificCourse(@PathParam("netId") String netId,
                                    @PathParam("CourseId") String course) {
        return lecturerService.getSpecificCourseOfLecturer(netId, course);
    }

    @GetMapping("/courses/getCandidateTas")
    public List<Student> getCandidateTas(@PathParam("netId") String netId,
                                         @PathParam("courseId") String course) {
        return lecturerService.getCandidateTaList(netId, course);
    }

    @PatchMapping("/courses/selectTa")
    public void selectTaForCourse(
            @PathParam("netId") String netId,
            @PathParam("courseId") String course,
            @PathParam("studentId") String studentNetId,
            @PathParam("hours") int hours) {
        lecturerService.chooseTa(netId, course, studentNetId, hours);
    }

    @PatchMapping("/courses/addCourse")
    public Lecturer addSpecificCourse(@PathParam("netId") String netId,
                                  @PathParam("courseId") String courseId) {
        return lecturerService.addSpecificCourse(netId, courseId);
    }

    @GetMapping("/getAverageRating")
    public double getAverageRating(@PathParam("netId") String netId,
                                   @PathParam("courseId") String course,
                                   @PathParam("studentId") String studentId) {
        return lecturerService.computeAverageRating(netId, course, studentId);
    }

    @GetMapping("/courses/recommendations")
    public List<Student> getRecommendations(@PathParam("netId") String netId,
                                            @PathParam("courseId") String course) {
        return lecturerService.getRecommendation(netId, course);
    }

    @GetMapping("/courses/getSize")
    public int getNumberOfTa(@PathParam("netId") String netId,
                             @PathParam("courseId") String course) {
        return lecturerService.getNumberOfNeededTas(netId, course);
    }

    @GetMapping("approveHours")
    public void approveHours(@PathParam("netId") String netId,
                             @RequestBody Contract contract) {
        lecturerService.approveHours(netId, contract);
    }
}
