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

/**
 * Suppress is used here to avoid mistake "duplicate netId". As netId is used quite often
 * as a header as an actual string, it doesn't make sense to out it in a variable.
 */

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RestController
@RequestMapping("/lecturer")
public class LecturerController {

    private final transient LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    /**
     * Add lecturer to a database.
     *
     * @param lecturer lecturer
     */
    @PostMapping("/addLecturer")
    public void addLecturer(@RequestBody Lecturer lecturer) {
        lecturerService.addLecturer(lecturer);
    }

    /**
     * Find all lecturers in a database.
     *
     * @return all lecturers
     */
    @GetMapping("/getAll")
    public List<Lecturer> findAll() {
        return lecturerService.findAll();
    }

    /**
     * Get specific lecturer using id.
     *
     * @param netId of a lecturer
     * @return lecturer
     */
    @GetMapping("/get")
    public Lecturer getLecturer(@RequestHeader("netId") String netId) {
        return lecturerService.findLecturerById(netId);
    }

    /**
     * Get all courses taught by the lecturer.
     *
     * @param netId of a lecturer
     * @return ids of courses taught by the lecturer
     */
    @GetMapping("/courses/getOwnCourses")
    public List<String> getOwnCourses(@RequestHeader("netId") String netId) {
        return lecturerService.getOwnCourses(netId);
    }

    /**
     * Get specific course taught by lecturer.
     *
     * @param netId of a lecturer
     * @param courseId id of a course
     * @return course
     */
    @GetMapping("/courses/getSpecificCourse")
    public Course getSpecificCourse(@RequestHeader("netId") String netId,
                                    @PathParam("courseId") String courseId) {
        return lecturerService.getSpecificCourseOfLecturer(netId, courseId);
    }

    /**
     * Get list of candidate tas.
     *
     * @param netId of a lecturer
     * @param courseId id of a course
     * @return ids of candidates Tas
     */
    @GetMapping("/courses/getCandidateTas")
    public Set<String> getCandidateTas(@RequestHeader("netId") String netId,
                                       @PathParam("courseId") String courseId) {
        return lecturerService.getCandidateTaList(netId, courseId);
    }

    /**
     * Select Ta for a specific course.
     *
     * @param netId of a lecturer
     * @param courseId id of course
     * @param studentId id of a student
     * @param hours list of hours
     */
    @PutMapping("/courses/selectTa")
    public void selectTaForCourse(
            @RequestHeader("netId") String netId,
            @PathParam("courseId") String courseId,
            @PathParam("studentId") String studentId,
            @PathParam("hours") float hours) {
        lecturerService.chooseTa(netId, courseId, studentId, hours);
    }

    /**
     * Add new course for a lecturer.
     *
     * @param netId of a lecturer
     * @param courseId id of a course
     * @return lecturer with new course
     */
    @PutMapping("/courses/addCourse")
    public Lecturer addSpecificCourse(@RequestHeader("netId") String netId,
                                      @PathParam("courseId") String courseId) {
        return lecturerService.addSpecificCourse(netId, courseId);
    }

    /**
     * Get average rating of particular student.
     *
     * @param netId of a lecturer
     * @param courseId id of a course
     * @param studentId id of a student
     * @return average rating of student
     */
    @GetMapping("/getAverageRating")
    public float getAverageRating(@RequestHeader("netId") String netId,
                                   @PathParam("courseId") String courseId,
                                   @PathParam("studentId") String studentId) {
        return lecturerService.getAverage(netId, courseId, studentId);
    }

    /**
     * Get recommendation list of students based on the strategy.
     *
     * @param netId of a lecturer
     * @param courseId id of a course
     * @param strategy to sort students
     * @return list of sorted students
     */
    @GetMapping("/courses/recommendations")
    public List<Student> getRecommendations(@RequestHeader("netId") String netId,
                                            @PathParam("courseId") String courseId,
                                            @PathParam("strategy") String strategy) {
        return lecturerService.getRecommendation(netId, courseId, strategy);
    }

    /**
     * Get needed number of tas.
     *
     * @param netId of a lecturer
     * @param courseId id of course
     * @return needed number of tas
     */
    @GetMapping("/courses/getSize")
    public int getNumberOfTa(@RequestHeader("netId") String netId,
                             @PathParam("courseId") String courseId) {
        return lecturerService.getNumberOfNeededTas(netId, courseId);
    }

    /**
     * Approve certain number of hours.
     *
     * @param netId of a lecturer
     * @param contract hours to approve
     */
    @PostMapping("/approveHours")
    public void approveHours(@RequestHeader("netId") String netId,
                             @RequestBody List<Hours> contract) {
        lecturerService.approveHours(netId, contract);
    }

    /**
     * Disapprove certain number of hours.
     *
     * @param netId of a lecturer
     * @param contract hours to disapprove
     */
    @PostMapping("/disapproveHours")
    public void disapprove(@RequestHeader("netId") String netId,
                             @RequestBody List<Hours> contract) {
        lecturerService.disapproveHours(netId, contract);
    }

    /**
     * Rate ta for a course.
     *
     * @param netId of a lecturer
     * @param courseId id of a course
     * @param studentId id of a student
     * @param rating to rate student
     */
    @GetMapping("/rateTa")
    public void rateTa(@RequestHeader("netId") String netId,
                        @PathParam("courseId") String courseId,
                        @PathParam("studentId") String studentId,
                       @PathParam("rating") float rating) {
        lecturerService.rateTa(netId, courseId, studentId, rating);
    }

    /**
     * View needed student.
     *
     * @param netId of a lecturer
     * @param courseId id of a course
     * @param studentId id of a student
     * @return student
     */
    @GetMapping("/view")
    public Student viewStudent(@RequestHeader("netId") String netId,
                               @PathParam("courseId") String courseId,
                               @PathParam("studentId") String studentId) {
        return lecturerService.viewStudent(netId, courseId, studentId);
    }
}
