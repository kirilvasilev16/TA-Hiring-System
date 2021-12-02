package course.controllers.exceptions;

public class CourseNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 42L;

    public CourseNotFoundException(String code) {
        super("Could not find a course with id " + code);
    }
}
