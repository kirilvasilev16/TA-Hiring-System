package course.exceptions;

public class FailedContractCreationException extends RuntimeException {
    static final long serialVersionUID = 1l;

    public FailedContractCreationException(String message) {
        super(message);
    }
}

