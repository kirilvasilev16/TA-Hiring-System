package course.exceptions;

public class FailedGetStudentRatingsException extends RuntimeException {
    static final long serialVersionUID = 1l;

    public FailedGetStudentRatingsException(String message) {
        super(message);
    }
}
