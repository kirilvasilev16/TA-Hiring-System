package course.controllers.exceptions;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String code) {
        super("Could not find a course with code " + code);
    }
}
