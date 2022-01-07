package course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class DeadlinePastException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public DeadlinePastException(String message) {
        super(message);
    }
}
