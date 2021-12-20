package course.exceptions;

public class FailedGetStudentRatingsException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public FailedGetStudentRatingsException(String message) {
        super(message);
    }
}
