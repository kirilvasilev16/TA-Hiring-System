package student.exceptions;

import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends EntityNotFoundException {

    static final long serialVersionUID = 1L;

    public StudentNotFoundException(String message) {
        super(message);
    }
}
