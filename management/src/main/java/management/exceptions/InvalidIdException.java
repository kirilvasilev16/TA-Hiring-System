package management.exceptions;

public class InvalidIdException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidIdException(String message) {
        super(message);
    }
}
