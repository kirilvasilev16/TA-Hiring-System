package course.controllers;


import course.entities.Course;
import course.exceptions.CourseAlreadyExistException;
import course.exceptions.CourseNotFoundException;
import course.exceptions.InvalidCandidateException;
import course.exceptions.InvalidHiringException;
import course.exceptions.InvalidLecturerException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("PMD.AvoidDuplicateLiterals") //for string literals in path param annotations
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
     * @return Course object of corresponding id
     */
    @GetMapping("get")
    public Course getCourse(@PathParam("courseId") String courseId) {
        Course c = courseService.findByCourseId(courseId);

        return c;
    }

    /**
     * Retrieve specific course student size.
     *
     * @param courseId String courseID
     * @return Integer course size
     */
    @GetMapping("size")
    public Integer getCourseSize(@PathParam("courseId") String courseId) {
        Course c = courseService.findByCourseId(courseId);

        return c.getCourseSize();
    }

    /**
     * Update specific course student size.
     *
     * @param courseId String courseID
     * @param size     Integer course size
     * @return true if updated
     */
    @PutMapping("updateSize")
    @Transactional
    public Boolean updateCourseSize(@PathParam("courseId") String courseId,
                                    @PathParam("size") Integer size) {
        Course c = courseService.findByCourseId(courseId);

        c.setCourseSize(size);

        courseService.updateCourseSize(courseId, size);
        return true;
    }

    /**
     * Retrieve lecturers of specific course.
     *
     * @param courseId String courseID
     * @return Set of Strings (lecturerIDs)
     */
    @GetMapping("lecturers")
    public Set<String> getLecturerSet(@PathParam("courseId") String courseId) {
        Course c = courseService.findByCourseId(courseId);

        return LecturerService.getLecturerSet(c);
    }

    /**
     * Retrieve number of required TAs for specific course.
     *
     * @param courseId String courseID
     * @return Integer value for required number of TAs
     */
    @GetMapping("requiredTas")
    public Integer getRequiredTas(@PathParam("courseId") String courseId) {
        Course c = courseService.findByCourseId(courseId);

        return c.getRequiredTas();
    }

    /**
     * Retrieve list of recommended TA Id's.
     *
     * @param courseId courseId
     * @param strategy desired sorting strategy
     * @param netId    netId of lecturer
     * @return Ordered list of TA's
     * @throws InvalidLecturerException if lecturer invalid
     */
    @GetMapping("taRecommendations")
    public List<String> getTaRecommendationList(@PathParam("courseId") String courseId,
                                                @PathParam("strategy") String strategy,
                                                @RequestHeader("netId") String netId)
            throws InvalidLecturerException {
        Course c = courseService.findByCourseId(courseId);

        validateLecturer(c, netId);

        return StudentService.getTaRecommendationList(c, strategy, communicationService);
    }

    /**
     * Create and Persist new Course in system.
     *
     * @param body CourseCreationBody object containing course information
     * @return Course object of course created
     * @throws CourseAlreadyExistException if course already exists
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")//PMD not recognising the try catch block
    @PostMapping("makeCourse")
    public Course makeCourse(@RequestBody CourseCreationBody body)
        throws CourseAlreadyExistException {
        Course c;
        String id = body.getCourseId();
        try {
            c = courseService.findByCourseId(id);
            throw new CourseAlreadyExistException(id);
        } catch (CourseNotFoundException e) {
            c = body.createCourse();
        }

        this.courseService.save(c);
        return c;
    }

    /**
     * Add student as Candidate TA for specific course.
     *
     * @param courseId  String courseID
     * @param studentId String studentID
     * @param studentCourses Set of strings (courseId) of courses students are already candidate for
     * @return true if student added as candidate
     * @throws InvalidCandidateException if student already hired as TA
     * @throws TooManyCoursesException if student is already candidate for 3 courses
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")//PMD not recognising null check before add call
    @PutMapping("addCandidateTa")
    public Boolean addCandidateTa(@PathParam("courseId") String courseId,
                                  @PathParam("studentId") String studentId,
                                  @RequestBody Set<String> studentCourses)
            throws InvalidCandidateException, TooManyCoursesException {
        Course c = courseService.findByCourseId(courseId);


        Set<Course> courses = getCourses(studentCourses, c);

        StudentService.addCandidate(c, studentId, dateService.getTodayDate(), courses);

        courseService.save(c);
        return true;
    }

    private Set<Course> getCourses(Set<String> studentCourses, Course c) {
        //validate input
        Set<Course> courses = new HashSet<>();
        for (String id : studentCourses) {
            Course current = courseService.findByCourseId(id);
            courses.add(current);
        }
        courses.add(c);
        return courses;
    }

    /**
     * Remove student as candidate TA for specific course.
     *
     * @param courseId  String courseID
     * @param studentId String studentID
     * @throws InvalidCandidateException if student not a candidate TA
     */
    @DeleteMapping("removeAsCandidate")
    @Transactional
    public void removeAsCandidate(@PathParam("courseId") String courseId,
                                  @PathParam("studentId") String studentId)
            throws InvalidCandidateException {
        Course c = courseService.findByCourseId(courseId);

        StudentService.removeCandidate(c, studentId);

        courseService.save(c);
    }

    /**
     * Retrieve TA average worked hours for specific course.
     *
     * @param courseId String courseID
     * @return float avg worked hours
     */
    @GetMapping("averageWorkedHours")
    public float getAverageWorkedHours(@PathParam("courseId") String courseId) {
        Course c = courseService.findByCourseId(courseId);

        return StudentService.getAverageWorkedHours(c, communicationService);

    }


    /**
     * Add lecturer to a specific course.
     *
     * @param courseId   String courseId
     * @param lecturerId String lecturerId
     * @return true is lecturer added
     */
    @PutMapping("addLecturer")
    public Boolean addLecturer(@PathParam("courseId") String courseId,
                               @PathParam("lecturerId") String lecturerId) {
        Course c = courseService.findByCourseId(courseId);

        LecturerService.addLecturer(c, lecturerId);

        courseService.save(c);
        return true;
    }

    /**
     * Retrieve all candidate TAs of specific course.
     *
     * @param courseId String courseId
     * @param netId    String lecturer netId
     * @return Set of strings (studentIds)
     * @throws InvalidLecturerException if lecturer doesnt belong to course
     */
    @GetMapping("candidates")
    public Set<String> getCandidateSet(@PathParam("courseId") String courseId,
                                       @RequestHeader("netId") String netId)
            throws InvalidLecturerException {
        Course c = courseService.findByCourseId(courseId);

        validateLecturer(c, netId);

        return StudentService.getCandidates(c);
    }

    /**
     * Retrieve all hired TAs of specific course.
     *
     * @param courseId String courseId
     * @param netId    String lecturerId
     * @return Set of strings (studentIds)
     * @throws InvalidLecturerException if lecturer doesnt belong to course
     */
    @GetMapping("tas")
    public Set<String> getTaSet(@PathParam("courseId") String courseId,
                                @RequestHeader("netId") String netId)
            throws InvalidLecturerException {
        Course c = courseService.findByCourseId(courseId);



        validateLecturer(c, netId);

        return StudentService.getTaSet(c);
    }


    /**
     * Hire candidate TA as TA.
     *
     * @param courseId  String courseId
     * @param studentId String studentId
     * @param hours     float contract hours
     * @param netId     String lecturer netId
     * @throws InvalidHiringException  if student already hired or not in course
     * @throws InvalidLecturerException if lecturer doesnt belong to course
     */
    @PutMapping("hireTa")
    public Boolean hireTa(@PathParam("courseId") String courseId,
                          @PathParam("studentId") String studentId,
                          @PathParam("hours") float hours,
                          @RequestHeader("netId") String netId)
            throws InvalidHiringException, InvalidLecturerException {
        Course c = courseService.findByCourseId(courseId);

        validateLecturer(c, netId);

        StudentService.hireTa(c, studentId, hours, communicationService);

        courseService.save(c);
        return true;
    }



    private void validateLecturer(Course c, String netId) {
        if (!LecturerService.containsLecturer(c, netId)) {
            throw new InvalidLecturerException("Lecturer not a staff of this course");
        }
    }


}
