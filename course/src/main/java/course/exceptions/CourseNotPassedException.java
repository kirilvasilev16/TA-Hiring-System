package course.exceptions;

public class CourseNotPassedException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public CourseNotPassedException(String message) {
        super(message);
    }
}
