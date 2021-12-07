package management.exceptions;

public class InvalidContractHoursException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidContractHoursException(String message) {
        super(message);
    }
}
