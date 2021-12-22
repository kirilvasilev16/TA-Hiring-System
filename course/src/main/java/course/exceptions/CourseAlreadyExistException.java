package course.exceptions;

public class CourseAlreadyExistException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public CourseAlreadyExistException(String message) {
        super(message);
    }
}
