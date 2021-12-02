package course.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import course.controllers.exceptions.CourseNotFoundException;
import course.controllers.strategies.ExperienceStrategy;
import course.controllers.strategies.GradeStrategy;
import course.controllers.strategies.RatingStrategy;
import course.controllers.strategies.TARecommendationStrategy;
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

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("courses")
public class CourseController {

    private final transient CourseService courseService;

    private static HttpClient client = HttpClient.newBuilder().build();
    private static Gson gson = new GsonBuilder()
            .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();

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

    @GetMapping("{code}/size")
    public Integer getCourseSize(@PathVariable String code)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) throw new CourseNotFoundException(code);

        return c.getCourseSize();
    }

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

    @GetMapping("{code}/lecturers")
    public Set<String> getLecturerSet(@PathVariable String code)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) throw new CourseNotFoundException(code);

        return LecturerService.getLecturerSet(c);
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

    @GetMapping("{code}/tarecommendations")
    public List<String> getTARecommendationList(@PathVariable String code, @PathVariable Integer strategy)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }

        return StudentService.getTARecommendationList(c, strategy);
    }

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

    @PostMapping("addcandidateta")
    public void addCandidateTA(@PathVariable String code, @RequestBody String student)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseID(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }

        StudentService.addCandidate(c, student);
    }

    @DeleteMapping("{code}/removeascandidate")
    @Transactional
    public void removeAsCandidate(@PathVariable String code, @RequestBody String student)
            throws CourseNotFoundException {
            Course c = courseService.findByCourseID(code);

            if (c == null) {
                throw new CourseNotFoundException(code);
            }

            StudentService.removeCandidate(c, student);
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

//        for(Management m : c.getHiredTAs()){
//            //avg += m.getWorkedHours
//        }
        avg /= StudentService.hiredTAs(c);

        return avg;
    }

}
