package course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class CourseAlreadyExistException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public CourseAlreadyExistException(String message) {
        super(message);
    }
}
