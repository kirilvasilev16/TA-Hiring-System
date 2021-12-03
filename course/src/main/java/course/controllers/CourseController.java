package course.controllers;

import course.exceptions.CourseNotFoundException;
import course.exceptions.InvalidCandidateException;
import course.exceptions.InvalidHiringException;
import course.entities.Course;
import course.services.LecturerService;
import course.services.StudentService;
import course.services.interfaces.CourseService;
import java.util.Set;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
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
     * @param courseId The id of the Course
     */
    @GetMapping("get")
    public Course getCourse(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        return c;
    }

    /**
     * Retrieve specific course student size.
     *
     * @param courseId String courseID
     * @return Integer course size
     * @throws CourseNotFoundException if course not found
     */
    @GetMapping("size")
    public Integer getCourseSize(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        return c.getCourseSize();
    }

    /**
     * Update specific course student size.
     *
     * @param courseId   String courseID
     * @param courseSize Integer course size
     * @throws CourseNotFoundException if course not found
     */
    @PatchMapping("updateSize")
    @Transactional
    public void updateCourseSize(@PathParam("courseId") String courseId,
                                 @PathParam("size") Integer courseSize)
            throws CourseNotFoundException {
        Course c = courseService.findByName(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        courseService.updateCourseSize(courseSize);

    }

    /**
     * Retrieve lecturers of specific course.
     *
     * @param courseId String courseID
     * @return Set of Strings (lecturerIDs)
     * @throws CourseNotFoundException if Course not found
     */
    @GetMapping("lecturers")
    public Set<String> getLecturerSet(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        return LecturerService.getLecturerSet(c);
    }

    /**
     * Retrieve number of required TAs for specific course.
     *
     * @param courseId String courseID
     * @return Integer value for required number of TAs
     * @throws CourseNotFoundException if Course not found
     */
    @GetMapping("requiredTas")
    public Integer getRequiredTas(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }
        return c.getRequiredTas();
    }

    // TODO: Will be further implemented for sprint 2
    /*
    @GetMapping("taRecommendation")
    public Integer getTARecommendation(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }
        return 1;
    }
    */


    /**
     * Create and Persist new Course in system.
     *
     * @param body CourseCreationBody object containing course information
     * @return String execution result message
     */
    @PostMapping("makeCourse")
    public String makeCourse(@RequestBody CourseCreationBody body) {
        Course c = courseService.findByCourseId(body.getCourseId());

        if (c != null) {
            return "Course with id " + body.getCourseId() + " already exists!";
        }
        c = new Course(body.getCourseId(), body.getName(), body.getCourseSize(),
                body.getLecturerSet(), body.getStartingDate());
        this.courseService.save(c);
        return "Course added successfully";
    }

    /**
     * Add student as Candidate TA for specific course.
     *
     * @param courseId String courseID
     * @param student  String studentID
     * @throws CourseNotFoundException   if Course not found
     * @throws InvalidCandidateException if student already hired as TA
     */
    @PostMapping("addCandidateTa")
    public void addCandidateTa(@PathParam("courseId") String courseId,
                               @PathParam("studentId") String student)
            throws CourseNotFoundException, InvalidCandidateException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        StudentService.addCandidate(c, student);
    }

    /**
     * Remove student as candidate TA for specific course.
     *
     * @param courseId String courseID
     * @param student  String studentID
     * @throws CourseNotFoundException   if course not found
     * @throws InvalidCandidateException if student not a candidate TA
     */
    @DeleteMapping("removeAsCandidate")
    @Transactional
    public void removeAsCandidate(@PathParam("courseId") String courseId,
                                  @PathParam("studentId") String student)
            throws CourseNotFoundException, InvalidCandidateException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        StudentService.removeCandidate(c, student);
    }

    /**
     * Retrieve TA average worked hours for specific course.
     *
     * @param courseId String courseID
     * @return float avg worked hours
     * @throws CourseNotFoundException if course not found
     */
    @GetMapping("averageWorkedHours")
    public float getAverageWorkedHours(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }
        if (c.getHiredTas().size() == 0) {
            return 0;
        }

        float avg = 0;

        /*
        for(Management m : c.getHiredTAs()){
            avg += m.getWorkedHours
        }
         */
        avg /= StudentService.hiredTas(c);

        return avg;
    }


    /**
     * Add lecturer to a specific course.
     *
     * @param courseId String courseId
     * @param lecturer String lecturerId
     * @throws CourseNotFoundException if course not found
     */
    @PostMapping("addLecturer")
    public void addLecturer(@PathParam("courseId") String courseId,
                            @PathParam("lecturerId") String lecturer)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        LecturerService.addLecturer(c, lecturer);
    }

    /**
     * Retrieve all candidate TAs of specific course.
     *
     * @param courseId String courseId
     * @return Set of strings (studentIds)
     * @throws CourseNotFoundException if course not found
     */
    @GetMapping("candidates")
    public Set<String> getCandidateSet(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        return StudentService.getCandidates(c);
    }

    /**
     * Retrieve all hired TAs of specific course.
     *
     * @param courseId String courseId
     * @return Set of strings (studentIds)
     * @throws CourseNotFoundException if course not found
     */
    @GetMapping("tas")
    public Set<String> getTaSet(@PathParam("courseId") String courseId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        return StudentService.getTaSet(c);
    }


    /**
     * Hire candidate TA as TA.
     *
     * @param courseId String courseId
     * @param student  String studentId
     * @param hours    float contract hours
     * @throws CourseNotFoundException if no courses found
     * @throws InvalidHiringException  if student already hired or not in course
     */
    @PostMapping("hireTa")
    public void hireTa(@PathParam("courseId") String courseId,
                       @PathParam("studentId") String student,
                       @PathParam("hours") float hours)
            throws CourseNotFoundException, InvalidHiringException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        StudentService.hireTa(c, student, hours);
    }


}
