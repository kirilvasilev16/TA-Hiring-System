package course.exceptions;

public class FailedGetHoursException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public FailedGetHoursException(String message) {
        super(message);
    }
}
