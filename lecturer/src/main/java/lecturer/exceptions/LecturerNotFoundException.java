package lecturer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class LecturerNotFoundException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public LecturerNotFoundException(String message) {
        super(message);
    }
}
