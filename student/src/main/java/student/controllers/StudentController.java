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
import student.entities.Student;
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

    /**
     * Instantiates a new Student controller.
     *
     * @param studentService the student service
     */
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
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
    public Set<Student> getMultiple(@RequestBody Set<String> ids) {
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
        return studentService.apply(netId, courseId);
    }

    @PutMapping("/removeApplication")
    public Student removeApplication(@RequestHeader("netId") String netId,
                                     @PathParam("courseId") String courseId) {
        return studentService.removeApplication(netId, courseId);
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

    @PutMapping("/declareHours")
    public void declareHours(@RequestHeader("netId") String netId,
                             @RequestBody String json) {
        studentService.declareHours(json);
    }
}
