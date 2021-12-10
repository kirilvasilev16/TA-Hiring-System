package course.exceptions;

public class InvalidLecturerException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidLecturerException(String message) {
        super(message);
    }
}
