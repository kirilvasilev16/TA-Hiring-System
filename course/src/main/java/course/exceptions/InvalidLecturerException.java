package course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class InvalidLecturerException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidLecturerException(String message) {
        super(message);
    }
}
