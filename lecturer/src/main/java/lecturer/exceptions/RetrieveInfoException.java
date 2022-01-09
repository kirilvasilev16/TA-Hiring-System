package lecturer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class RetrieveInfoException extends InternalError {
    static final long serialVersionUID = 1L;

    public RetrieveInfoException(String message) {
        super(message);
    }
}
