package course.exceptions;

public class FailedGetStudentListException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public FailedGetStudentListException(String message) {
        super(message);
    }
}
