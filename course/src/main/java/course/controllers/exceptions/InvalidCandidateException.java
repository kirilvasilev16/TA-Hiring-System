package course.controllers.exceptions;

public class InvalidCandidateException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidCandidateException(String message) {
        super(message);
    }
}
