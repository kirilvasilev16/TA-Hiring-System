package course.exceptions;

public class FailedUpdateStudentEmploymentException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public FailedUpdateStudentEmploymentException(String message) {
        super(message);
    }
}
