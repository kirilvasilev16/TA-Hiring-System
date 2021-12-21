package course.exceptions;

public class DeadlinePastException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public DeadlinePastException(String message) {
        super(message);
    }
}
