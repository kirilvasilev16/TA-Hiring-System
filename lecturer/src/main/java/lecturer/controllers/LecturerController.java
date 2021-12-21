package lecturer.controllers;

import java.util.List;
import java.util.Set;
import javax.websocket.server.PathParam;
import lecturer.entities.Contract;
import lecturer.entities.Course;
import lecturer.entities.Lecturer;
import lecturer.entities.Student;
import lecturer.services.LecturerService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Lecturer getLecturer(@RequestHeader("netId") String netId) {
        return lecturerService.findLecturerById(netId);
    }

    @GetMapping("/courses/getOwnCourses")
    public List<String> getOwnCourses(@RequestHeader("netId") String netId) {
        return lecturerService.getOwnCourses(netId);
    }

    @GetMapping("/courses/getSpecificCourse")
    public Course getSpecificCourse(@RequestHeader("netId") String netId,
                                    @PathParam("courseId") String courseId) {
        return lecturerService.getSpecificCourseOfLecturer(netId, courseId);
    }

    @GetMapping("/courses/getCandidateTas")
    public Set<String> getCandidateTas(@RequestHeader("netId") String netId,
                                       @PathParam("courseId") String courseId) {
        return lecturerService.getCandidateTaList(netId, courseId);
    }

    @PutMapping("/courses/selectTa")
    public void selectTaForCourse(
            @RequestParam("netId") String netId,
            @RequestHeader("Authorization") String auth,
            @PathParam("courseId") String courseId,
            @PathParam("studentId") String studentId,
            @PathParam("hours") float hours) {
        lecturerService.chooseTa(netId, courseId, studentId, hours, auth);
    }

    @PatchMapping("/courses/addCourse")
    public Lecturer addSpecificCourse(@RequestHeader("netId") String netId,
                                      @PathParam("courseId") String courseId) {
        return lecturerService.addSpecificCourse(netId, courseId);
    }

    @GetMapping("/getAverageRating")
    public double getAverageRating(@RequestHeader("netId") String netId,
                                   @PathParam("courseId") String course,
                                   @PathParam("studentId") String studentId) {
        return lecturerService.getAverage(netId, course, studentId);
    }

    @GetMapping("/courses/recommendations")
    public List<Student> getRecommendations(@RequestHeader("netId") String netId,
                                            @PathParam("courseId") String course,
                                            @PathParam("strategy") int strategy) {
        return lecturerService.getRecommendation(netId, course, strategy);
    }

    @GetMapping("/courses/getSize")
    public int getNumberOfTa(@RequestHeader("netId") String netId,
                             @PathParam("courseId") String course) {
        return lecturerService.getNumberOfNeededTas(netId, course);
    }

    @GetMapping("approveHours")
    public void approveHours(@RequestHeader("netId") String netId,
                             @RequestBody Contract contract) {
        lecturerService.approveHours(netId, contract);
    }
}
