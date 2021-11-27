package course.controllers;

import course.controllers.exceptions.CourseNotFoundException;
import course.entities.Course;
import course.services.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("courses")
public class CourseController {

    private final CourseService courseService;

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
    public Course getLecture(@PathVariable String code)
            throws CourseNotFoundException {
        Course c = courseService.findByCourseName(code);

        if (c == null) {
            throw new CourseNotFoundException(code);
        }
        return c;
    }
}
