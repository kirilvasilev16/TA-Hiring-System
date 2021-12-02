package course.controllers;

import course.controllers.exceptions.CourseNotFoundException;
import course.controllers.exceptions.InvalidCandidateException;
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
     * @param code The id of the Course
     */
    @GetMapping("{code}")
    public Course getCourse(@PathVariable String code)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) throw new CourseNotFoundException(code);

        return c;
    }

    /**
     * Retrieve specific course student size
     * @param code String courseID
     * @return Integer course size
     * @throws CourseNotFoundException if course not found
     */
    @GetMapping("{code}/size")
    public Integer getCourseSize(@PathVariable String code)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) throw new CourseNotFoundException(code);

        return c.getCourseSize();
    }

    /**
     * Update specific course student size
     * @param code String courseID
     * @param courseSize Integer course size
     * @throws CourseNotFoundException if course not found
     */
    @PatchMapping("{code}/updatesize")
    @Transactional
    public void updateCourseSize(@PathVariable String code, @RequestParam Integer courseSize)
            throws CourseNotFoundException {
        Course c = courseService.findByName(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }

        courseService.updateCourseSize(courseSize);

    }

    /**
     * Retrieve lecturers of specific course
     * @param code String courseID
     * @return Set of Strings (lecturerIDs)
     * @throws CourseNotFoundException if Course not found
     */
    @GetMapping("{code}/lecturers")
    public Set<String> getLecturerSet(@PathVariable String code)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) throw new CourseNotFoundException(code);

        return LecturerService.getLecturerSet(c);
    }

    /**
     * Retrieve number of required TAs for specific course
     * @param code String courseID
     * @return Integer value for required number of TAs
     * @throws CourseNotFoundException if Course not found
     */
    @GetMapping("{code}/requiredtas")
    public Integer getRequiredTAs(@PathVariable String code)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }
        return c.getRequiredTAs();
    }

    @GetMapping("{code}/tarecommendation")
    public Integer getTARecommendation(@PathVariable String code)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }
        return Math.max(1, c.getCourseSize() % 20 < 10 ? c.getCourseSize() / 20 : (c.getCourseSize() / 20) + 1);
    }

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
     * @param code String courseID
     * @param student String studentID
     * @throws CourseNotFoundException if Course not found
     * @throws InvalidCandidateException if student already hired as TA
     */
    @PostMapping("addcandidateta")
    public void addCandidateTA(@PathVariable String code, @RequestBody String student)
            throws CourseNotFoundException, InvalidCandidateException {
        Course c = courseService.findByCourseID(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }

        StudentService.addCandidate(c, student);
    }

    /**
     * Remove student as candidate TA for specific course
     * @param code String courseID
     * @param student String studentID
     * @throws CourseNotFoundException if course not found
     * @throws InvalidCandidateException if student not a candidate TA
     */
    @DeleteMapping("{code}/removeascandidate")
    @Transactional
    public void removeAsCandidate(@PathVariable String code, @RequestBody String student)
            throws CourseNotFoundException, InvalidCandidateException {
        Course c = courseService.findByCourseID(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }

        StudentService.removeCandidate(c, student);
    }

    /**
     * Retrieve TA average worked hours for specific course
     * @param code String courseID
     * @return float avg worked hours
     * @throws CourseNotFoundException if course not found
     */
    @GetMapping("{code}/averageworkedhours")
    public float getAverageWorkedHours(@PathVariable String code)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }
        if(c.getHiredTAs().size() == 0) return 0;

        float avg = 0;

//        for(Management m : c.getHiredTAs()){
//            //avg += m.getWorkedHours
//        }
        avg /= StudentService.hiredTAs(c);

        return avg;
    }

}
