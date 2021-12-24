package course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FailedUpdateStudentEmploymentException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public FailedUpdateStudentEmploymentException(String message) {
        super(message);
    }
}
