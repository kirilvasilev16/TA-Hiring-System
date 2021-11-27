package management.exceptions;

public class InvalidRatingException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidRatingException(String message) {
        super(message);
    }
}
