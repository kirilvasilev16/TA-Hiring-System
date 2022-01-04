package management.exceptions;

public class InvalidDisapprovedHoursException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidDisapprovedHoursException(String message) {
        super(message);
    }
}
