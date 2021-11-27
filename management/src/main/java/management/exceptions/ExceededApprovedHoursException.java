package management.exceptions;

public class ExceededApprovedHoursException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public ExceededApprovedHoursException(String message) {
        super(message);
    }
}
