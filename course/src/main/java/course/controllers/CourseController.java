package course.controllers;

import course.controllers.exceptions.CourseNotFoundException;
import course.controllers.exceptions.InvalidCandidateException;
import course.controllers.exceptions.InvalidHiringException;
import course.entities.Course;
import course.entities.Lecturer;
import course.entities.Management;
import course.entities.Student;
import course.services.LecturerService;
import course.services.StudentService;
import course.services.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.util.Set;

@RestController
@RequestMapping("courses")
public class CourseController {

    private final transient CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Display info about a course.
     *
     * @param courseID The id of the Course
     */
    @GetMapping("get")
    public Course getCourse(@PathParam("courseId") String courseID)
            throws CourseNotFoundException{
        Course c = courseService.findByCourseID(courseID);

        if (c == null) throw new CourseNotFoundException(courseID);

        return c;
    }

    /**
     * Retrieve specific course student size
     * @param courseId String courseID
     * @return Integer course size
     * @throws CourseNotFoundException if course not found
     */
    @GetMapping("size")
    public Integer getCourseSize(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(courseId);

        if (c == null) throw new CourseNotFoundException(courseId);

        return c.getCourseSize();
    }

    /**
     * Update specific course student size
     * @param courseId String courseID
     * @param courseSize Integer course size
     * @throws CourseNotFoundException if course not found
     */
    @PatchMapping("updatesize")
    @Transactional
    public void updateCourseSize(@PathParam("courseId") String courseId, @PathParam("size") Integer courseSize)
            throws CourseNotFoundException {
        Course c = courseService.findByName(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        courseService.updateCourseSize(courseSize);

    }

    /**
     * Retrieve lecturers of specific course
     * @param courseId String courseID
     * @return Set of Strings (lecturerIDs)
     * @throws CourseNotFoundException if Course not found
     */
    @GetMapping("lecturers")
    public Set<String> getLecturerSet(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(courseId);

        if (c == null) throw new CourseNotFoundException(courseId);

        return LecturerService.getLecturerSet(c);
    }

    /**
     * Retrieve number of required TAs for specific course
     * @param courseId String courseID
     * @return Integer value for required number of TAs
     * @throws CourseNotFoundException if Course not found
     */
    @GetMapping("requiredtas")
    public Integer getRequiredTAs(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }
        return c.getRequiredTAs();
    }

    /**
     * Will be further implemented for sprint 2
     */
//    @GetMapping("tarecommendation")
//    public Integer getTARecommendation(@PathParam("courseId") String courseId)
//            throws CourseNotFoundException {
//        Course c = courseService.findByCourseID(courseId);
//
//        if (c == null) {
//            throw new CourseNotFoundException(courseId);
//        }
//        return Math.max(1, c.getCourseSize() % 20 < 10 ? c.getCourseSize() / 20 : (c.getCourseSize() / 20) + 1);
//    }

    /**
     * Create and Persist new Course in system
     * @param body CourseCreationBody object containing course information
     * @return String execution result message
     */
    @PostMapping("makeCourse")
    public String makeCourse(@RequestBody CourseCreationBody body){
        Course c = courseService.findByCourseID(body.getCourseID());

        if (c != null) {
            return "Course with id " + body.getCourseID() + " already exists!";
        }
        c = new Course(body.getCourseID(), body.getName(),body.getCourseSize() ,body.getLecturerSet(), body.getStartingDate());
        this.courseService.save(c);
        return "Course added successfully";
    }

    /**
     * Add student as Candidate TA for specific course
     * @param courseId String courseID
     * @param student String studentID
     * @throws CourseNotFoundException if Course not found
     * @throws InvalidCandidateException if student already hired as TA
     */
    @PostMapping("addcandidateta")
    public void addCandidateTA(@PathParam("courseId") String courseId, @PathParam("studentId") String student)
            throws CourseNotFoundException, InvalidCandidateException {
        Course c = courseService.findByCourseID(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        StudentService.addCandidate(c, student);
    }

    /**
     * Remove student as candidate TA for specific course
     * @param courseId String courseID
     * @param student String studentID
     * @throws CourseNotFoundException if course not found
     * @throws InvalidCandidateException if student not a candidate TA
     */
    @DeleteMapping("removeascandidate")
    @Transactional
    public void removeAsCandidate(@PathParam("courseId") String courseId, @PathParam("studentId") String student)
            throws CourseNotFoundException, InvalidCandidateException {
        Course c = courseService.findByCourseID(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        StudentService.removeCandidate(c, student);
    }

    /**
     * Retrieve TA average worked hours for specific course
     * @param courseId String courseID
     * @return float avg worked hours
     * @throws CourseNotFoundException if course not found
     */
    @GetMapping("averageworkedhours")
    public float getAverageWorkedHours(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }
        if(c.getHiredTAs().size() == 0) return 0;

        float avg = 0;

//        for(Management m : c.getHiredTAs()){
//            //avg += m.getWorkedHours
//        }
        avg /= StudentService.hiredTAs(c);

        return avg;
    }


    /**
     * Add lecturer to a specific course
     * @param courseId String courseId
     * @param lecturer String lecturerId
     * @throws CourseNotFoundException if course not found
     */
    @PostMapping("addlecturer")
    public void addLecturer(@PathParam("courseId") String courseId, @PathParam("lecturerId") String lecturer)
            throws CourseNotFoundException{
        Course c = courseService.findByCourseID(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        LecturerService.addLecturer(c, lecturer);
    }

    /**
     * Retrieve all candidate TAs of specific course
     * @param courseId String courseId
     * @return Set of strings (studentIds)
     * @throws CourseNotFoundException if course not found
     */
    @GetMapping("candidates")
    public Set<String> getCandidateSet(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(courseId);

        if (c == null) throw new CourseNotFoundException(courseId);

        return StudentService.getCandidates(c);
    }

    /**
     * Retrieve all hired TAs of specific course
     * @param courseId String courseId
     * @return Set of strings (studentIds)
     * @throws CourseNotFoundException if course not found
     */
    @GetMapping("tas")
    public Set<String> getTASet(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(courseId);

        if (c == null) throw new CourseNotFoundException(courseId);

        return StudentService.getTASet(c);
    }


    /**
     * Hire candidate TA as TA
     * @param courseId String courseId
     * @param student String studentId
     * @param hours float contract hours
     * @throws CourseNotFoundException if no courses found
     * @throws InvalidHiringException if student already hired or not in course
     */
    @PostMapping("hireTA")
    public void hireTA(@PathParam("courseId") String courseId,
                       @PathParam("studentId") String student,
                       @PathParam("hours") float hours)
            throws CourseNotFoundException, InvalidHiringException {
        Course c = courseService.findByCourseID(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        StudentService.hireTA(c, student, hours);
    }









}
