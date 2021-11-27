package management.exceptions;

public class ExceededContractHoursException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public ExceededContractHoursException(String message) {
        super(message);
    }
}
