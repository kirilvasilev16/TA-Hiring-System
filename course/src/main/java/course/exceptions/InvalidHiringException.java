package course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidHiringException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidHiringException(String message) {
        super(message);
    }
}
