package student.controllers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import student.entities.Management;
import student.entities.Student;
import student.services.CouplingService;
import student.services.StudentService;


/**
 * The type Student controller.
 */
@RestController
@RequestMapping("/student")
// PMD doesn't want duplicate "netId" literals,
// but since you cannot use field references inside annotations, we suppress it
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class StudentController {

    private final transient StudentService studentService;
    private final transient CouplingService couplingService;

    /**
     * Instantiates a new Student controller.
     *
     * @param studentService the student service
     */
    @Autowired
    public StudentController(StudentService studentService,
                             CouplingService couplingService) {
        this.studentService = studentService;
        this.couplingService = couplingService;
    }

    /**
     * Gets student.
     *
     * @param id the id
     * @return the student
     */
    @GetMapping("/get")
    public Student getStudent(@PathParam("id") String id) {
        return studentService.getStudent(id);
    }

    /**
     * Gets all students in db.
     *
     * @return list of all students
     */
    @GetMapping("/getAll")
    public List<Student> getAll() {
        return studentService.getAll();
    }

    /**
     * Gets all students corresponding to given ids from http request body.
     * Used by course microservice.
     *
     * @param ids the ids
     * @return set of students
     */
    @PostMapping("/getMultiple")
    public List<Student> getMultiple(@RequestBody List<String> ids) {
        return studentService.getMultiple(ids);
    }

    /**
     * Gets passed courses.
     * This is an open endpoint
     *
     * @param id the id
     * @return the passed courses
     */
    @GetMapping("/getPassedCourses")
    public Map<String, Float> getPassedCourses(@RequestHeader("netId") String id) {
        return studentService.getPassedCourses(id);
    }

    /**
     * Gets candidate courses.
     *
     * @param id the id
     * @return the candidate courses
     */
    @GetMapping("/getCandidateCourses")
    public Set<String> getCandidateCourses(@PathParam("id") String id) {
        return studentService.getCandidateCourses(id);
    }

    /**
     * Gets ta courses.
     *
     * @param id the id
     * @return the ta courses
     */
    @GetMapping("/getTACourses")
    public Set<String> getTaCourses(@PathParam("id") String id) {
        return studentService.getTaCourses(id);
    }

    /**
     * Adds student to the db.
     *
     * @param student the student
     * @return the student
     */
    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    /**
     * Tries to apply a student as TA for a course.
     * This is an open endpoint
     *
     * @param netId    the net id of the student
     * @param courseId the course id
     * @return the (updated) student
     */
    @PutMapping("/apply")
    public Student apply(@RequestHeader("netId") String netId,
                         @PathParam("courseId") String courseId) {
        return couplingService.apply(netId, courseId);
    }

    /**
     * Removes the student as candidate for a course.
     * and sends a request for the same to Course microservice as well.
     *
     * @param netId    the net id
     * @param courseId the course id
     * @return the student
     */
    @PutMapping("/removeApplication")
    public Student removeApplication(@RequestHeader("netId") String netId,
                                     @PathParam("courseId") String courseId) {
        return couplingService.removeApplication(netId, courseId);
    }

    /**
     * Tries to accept a student as TA for a course.
     *
     * @param netId    the net id of the student
     * @param courseId the course id
     * @return the (updated) student
     */
    @PutMapping("/accept")
    public Student accept(@PathParam("netId") String netId,
                          @PathParam("courseId") String courseId) {
        return studentService.accept(netId, courseId);
    }

    /**
     * Sends a request to the Management microservice for declaring hours.
     *
     * @param netId the net id
     * @param json  the json containing Hours data
     */
    @PutMapping("/declareHours")
    public void declareHours(@RequestHeader("netId") String netId,
                             @RequestBody String json) {
        couplingService.declareHours(json);
    }

    /**
     * Sends a request to the Course microservice for getting the average worked hours.
     *
     * @param netId    the net id
     * @param courseId the course id
     * @return the average worked hours for given course
     */
    @GetMapping("averageWorkedHours")
    public float averageWorkedHours(@RequestHeader("netId") String netId,
                                    @PathParam("courseId") String courseId) {
        return couplingService.averageWorkedHours(courseId);
    }


    /**
     * Sends request to Management for getting all contract info for a student on a course.
     *
     * @param netId    the net id
     * @param courseId the course id
     * @return the Management object
     */
    @GetMapping("getManagement")
    public Management getManagement(@RequestHeader("netId") String netId,
                                    @PathParam("courseId") String courseId) {
        return couplingService.getManagement(netId, courseId);
    }
}
