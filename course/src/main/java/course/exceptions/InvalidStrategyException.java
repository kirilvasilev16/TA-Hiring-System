package course.exceptions;

public class InvalidStrategyException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidStrategyException(String message) {
        super(message);
    }
}
