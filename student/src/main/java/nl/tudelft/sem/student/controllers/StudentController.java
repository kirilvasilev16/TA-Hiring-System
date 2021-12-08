package nl.tudelft.sem.student.controllers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.websocket.server.PathParam;
import nl.tudelft.sem.student.entities.Student;
import nl.tudelft.sem.student.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Student controller.
 */
@RestController
@RequestMapping("/student")
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
    @GetMapping("getAll")
    public List<Student> getAll() {
        return studentService.getAll();
    }

    /**
     * Gets passed courses.
     *
     * @param id the id
     * @return the passed courses
     */
    @GetMapping("/getPassedCourses")
    public Map<String, Float> getPassedCourses(@PathParam("id") String id) {
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
     *
     * @param netId    the net id of the student
     * @param courseId the course id
     * @return the (updated) student
     */
    @PutMapping("/apply")
    public Student apply(@PathParam("netId") String netId,
                         @PathParam("courseId") String courseId) {
        return studentService.apply(netId, courseId);
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
}
