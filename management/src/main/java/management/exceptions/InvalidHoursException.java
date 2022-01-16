package management.exceptions;

public class InvalidHoursException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidHoursException(String message) {
        super(message);
    }
}
