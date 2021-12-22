package lecturer.controllers;

import java.util.List;
import java.util.Set;
import javax.websocket.server.PathParam;
import lecturer.entities.Course;
import lecturer.entities.Hours;
import lecturer.entities.Lecturer;
import lecturer.entities.Student;
import lecturer.services.LecturerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    //tested
    @PostMapping("/addLecturer")
    public void addLecturer(@RequestBody Lecturer lecturer) {
        lecturerService.addLecturer(lecturer);
    }

    //tested
    @GetMapping("/getAll")
    public List<Lecturer> findAll() {
        return lecturerService.findAll();
    }

    //tested
    @GetMapping("/get")
    public Lecturer getLecturer(@RequestHeader("netId") String netId) {
        return lecturerService.findLecturerById(netId);
    }

    //tested
    @GetMapping("/courses/getOwnCourses")
    public List<String> getOwnCourses(@RequestHeader("netId") String netId) {
        return lecturerService.getOwnCourses(netId);
    }

    //tested
    @GetMapping("/courses/getSpecificCourse")
    public Course getSpecificCourse(@RequestHeader("netId") String netId,
                                    @PathParam("courseId") String courseId) {
        return lecturerService.getSpecificCourseOfLecturer(netId, courseId);
    }

    //tested
    @GetMapping("/courses/getCandidateTas")
    public Set<String> getCandidateTas(@RequestHeader("netId") String netId,
                                       @PathParam("courseId") String courseId) {
        return lecturerService.getCandidateTaList(netId, courseId);
    }

    //tested
    @PutMapping("/courses/selectTa")
    public void selectTaForCourse(
            @RequestHeader("netId") String netId,
            @PathParam("courseId") String courseId,
            @PathParam("studentId") String studentId,
            @PathParam("hours") float hours) {
        lecturerService.chooseTa(netId, courseId, studentId, hours);
    }

    //tested
    @PutMapping("/courses/addCourse")
    public Lecturer addSpecificCourse(@RequestHeader("netId") String netId,
                                      @PathParam("courseId") String courseId) {
        return lecturerService.addSpecificCourse(netId, courseId);
    }

    //what to do if management object does not exist
    @GetMapping("/getAverageRating")
    public float getAverageRating(@RequestHeader("netId") String netId,
                                   @PathParam("courseId") String courseId,
                                   @PathParam("studentId") String studentId) {
        return lecturerService.getAverage(netId, courseId, studentId);
    }

    //course strategy needs fixing
    @GetMapping("/courses/recommendations")
    public List<Student> getRecommendations(@RequestHeader("netId") String netId,
                                            @PathParam("courseId") String courseId,
                                            @PathParam("strategy") String strategy) {
        return lecturerService.getRecommendation(netId, courseId, strategy);
    }

    //tested
    @GetMapping("/courses/getSize")
    public int getNumberOfTa(@RequestHeader("netId") String netId,
                             @PathParam("courseId") String courseId) {
        return lecturerService.getNumberOfNeededTas(netId, courseId);
    }

    //needs testing !!!
    @PostMapping("/approveHours")
    public void approveHours(@RequestHeader("netId") String netId,
                             @RequestBody List<Hours> contract) {
        lecturerService.approveHours(netId, contract);
    }

    //tested
    @GetMapping("/rateTa")
    public void rateTa(@RequestHeader("netId") String netId,
                        @PathParam("courseId") String courseId,
                        @PathParam("studentId") String studentId,
                       @PathParam("rating") float rating) {
        lecturerService.rateTa(netId, courseId, studentId, rating);
    }
}
