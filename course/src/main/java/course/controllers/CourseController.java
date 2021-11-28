package course.controllers;

import course.controllers.exceptions.CourseNotFoundException;
import course.entities.Course;
import course.entities.Lecturer;
import course.entities.Management;
import course.entities.Student;
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

        if (c == null) {
            throw new CourseNotFoundException(code);
        }
        return c;
    }

    @GetMapping("{code}/size")
    public Integer getCourseSize(@PathVariable String code)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }
        return c.getCourseSize();
    }

//    @PatchMapping("{code}/updatesize")
//    @Transactional
//    public void updateCourseSize(@PathVariable String code, @RequestBody Integer courseSize)
//            throws CourseNotFoundException {
//        Course c = courseService.findByName(code);
//
//        if (c == null) {
//            throw new CourseNotFoundException(code);
//        }
//
//        courseService.updateCourseSize(courseSize);
//
//    }

    @GetMapping("{code}/lecturers")
    public Set<Lecturer> getLecturerSet(@PathVariable String code)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }
        return c.getLecturerSet();
    }

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

    @PostMapping("makeCourse")
    public String makeCourse(@RequestBody CourseCreationBody body){
        Course c = courseService.findByCourseID(body.getCourseID());

        if (c != null) {
            return "Course with id " + body.getCourseID() + " already exists!";
        }
        c = new Course(body.getCourseID(), body.getName(), body.getLecturerSet(), body.getStartingDate());
        this.courseService.save(c);
        return "Course added successfully";
    }

    @PostMapping("addcandidateta")
    public void addCandidateTA(@PathVariable String code, @RequestBody Student student)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }

        c.getCandidateTAs().add(student);
    }

    @DeleteMapping("{code}/removeascandidate")
    @Transactional
    public void removeAsCandidate(@PathVariable String code, @RequestBody Student student)
            throws CourseNotFoundException {
            Course c = courseService.findByCourseID(code);

            if (c == null) {
                throw new CourseNotFoundException(code);
            }

            c.getCandidateTAs().remove(student);
        }

    @GetMapping("{code}/averageworkedhours")
    public float getAverageWorkedHours(@PathVariable String code)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }
        if(c.getHiredTAs().size() == 0) return 0;

        float avg = 0;

        for(Management m : c.getHiredTAs()){
            //avg += m.getWorkedHours
        }
        avg /= c.getHiredTAs().size();

        return avg;
    }

}
