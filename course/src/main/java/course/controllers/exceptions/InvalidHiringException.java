package course.controllers.exceptions;

public class InvalidHiringException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidHiringException(String message) {
        super(message);
    }
}
