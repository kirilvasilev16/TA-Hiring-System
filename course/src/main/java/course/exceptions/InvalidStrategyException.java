package course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidStrategyException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public InvalidStrategyException(String message) {
        super(message);
    }
}
