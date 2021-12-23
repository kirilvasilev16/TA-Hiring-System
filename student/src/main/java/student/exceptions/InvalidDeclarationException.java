package student.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidDeclarationException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public InvalidDeclarationException(String message) {
        super(message);
    }
}
