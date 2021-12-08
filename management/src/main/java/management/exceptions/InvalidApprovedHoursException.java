package management.exceptions;

public class InvalidApprovedHoursException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidApprovedHoursException(String message) {
        super(message);
    }
}
