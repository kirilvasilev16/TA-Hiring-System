package course.controllers;


import course.entities.Course;
import course.exceptions.CourseNotFoundException;
import course.exceptions.InvalidCandidateException;
import course.exceptions.InvalidHiringException;
import course.exceptions.TooManyCoursesException;
import course.services.CommunicationService;
import course.services.CourseService;
import course.services.DateService;
import course.services.LecturerService;
import course.services.StudentService;
import java.util.HashSet;
import java.util.List;
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
    private final transient CommunicationService communicationService;
    private final transient DateService dateService;

    /**
     * Course Controller constructor.
     *
     * @param courseService        CourseService object
     * @param communicationService CommunicationService object
     * @param dateService          DateService object
     */
    @Autowired
    public CourseController(CourseService courseService, CommunicationService communicationService,
                            DateService dateService) {
        this.courseService = courseService;
        this.communicationService = communicationService;
        this.dateService = dateService;
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
     * @param size Integer course size
     * @throws CourseNotFoundException if course not found
     */
    @PatchMapping("updateSize")
    @Transactional
    public void updateCourseSize(@PathParam("courseId") String courseId,
                                 @PathParam("size") Integer size)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }
        c.setCourseSize(size);

        courseService.updateCourseSize(courseId, size);

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

    /**
     * Retrieve list of recommended TA Id's.
     *
     * @param courseId courseId
     * @param strategy desired sorting strategy
     * @return Ordered list of TA's
     */
    @GetMapping("taRecommendations")
    public List<String> getTaRecommendationList(@PathParam("courseId") String courseId,
                                                @PathParam("strategy") String strategy)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        return StudentService.getTaRecommendationList(c, strategy, communicationService);
    }

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
                body.getLecturerSet(), body.getStartingDate(), body.getQuarter());
        this.courseService.save(c);
        return "Course added successfully";
    }

    /**
     * Add student as Candidate TA for specific course.
     *
     * @param courseId  String courseID
     * @param studentId String studentID
     * @throws CourseNotFoundException   if Course not found
     * @throws InvalidCandidateException if student already hired as TA
     */
    @SuppressWarnings("PMD")
    @PostMapping("addCandidateTa")
    public void addCandidateTa(@PathParam("courseId") String courseId,
                               @PathParam("studentId") String studentId,
                               @RequestBody Set<String> studentCourses)
            throws CourseNotFoundException, InvalidCandidateException, TooManyCoursesException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        //validate input
        Set<Course> courses = new HashSet<>();
        for (String id : studentCourses) {
            Course current = courseService.findByCourseId(id);
            if (current == null) {
                throw new CourseNotFoundException(id);
            }
            courses.add(current);
        }

        StudentService.checkQuarterCapacity(courses);
        StudentService.addCandidate(c, studentId, dateService.getTodayDate());

        //courseService.updateCandidateTas(courseId, c.getCandidateTas());
        courseService.save(c);
    }

    /**
     * Remove student as candidate TA for specific course.
     *
     * @param courseId  String courseID
     * @param studentId String studentID
     * @throws CourseNotFoundException   if course not found
     * @throws InvalidCandidateException if student not a candidate TA
     */
    @DeleteMapping("removeAsCandidate")
    @Transactional
    public void removeAsCandidate(@PathParam("courseId") String courseId,
                                  @PathParam("studentId") String studentId)
            throws CourseNotFoundException, InvalidCandidateException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        StudentService.removeCandidate(c, studentId);

        //courseService.updateCandidateTas(courseId, c.getCandidateTas());
        courseService.save(c);
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

        return StudentService.getAverageWorkedHours(c, communicationService);

    }


    /**
     * Add lecturer to a specific course.
     *
     * @param courseId   String courseId
     * @param lecturerId String lecturerId
     * @throws CourseNotFoundException if course not found
     */
    @PostMapping("addLecturer")
    public void addLecturer(@PathParam("courseId") String courseId,
                            @PathParam("lecturerId") String lecturerId)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        LecturerService.addLecturer(c, lecturerId);

        //courseService.updateLecturerSet(courseId, c.getLecturerSet());
        courseService.save(c);
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
     * @param courseId   String courseId
     * @param studentId  String studentId
     * @param lecturerId String lecturerId
     * @param hours      float contract hours
     * @throws CourseNotFoundException if no courses found
     * @throws InvalidHiringException  if student already hired or not in course
     */
    @PostMapping("hireTa")
    public void hireTa(@PathParam("courseId") String courseId,
                       @PathParam("studentId") String studentId,
                       @PathParam("lecturerId") String lecturerId,
                       @PathParam("hours") float hours)
            throws CourseNotFoundException, InvalidHiringException {
        Course c = courseService.findByCourseId(courseId);

        if (c == null) {
            throw new CourseNotFoundException(courseId);
        }

        StudentService.hireTa(c, studentId, lecturerId, hours, communicationService);

        //courseService.updateHireTas(courseId, c.getHiredTas());
        //courseService.updateCandidateTas(courseId, c.getCandidateTas());
        courseService.save(c);
    }


}
