package course.exceptions;

public class TooManyCoursesException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public TooManyCoursesException(String message) {
        super(message);
    }
}
