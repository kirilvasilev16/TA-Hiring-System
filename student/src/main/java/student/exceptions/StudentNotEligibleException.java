package student.exceptions;

import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class StudentNotEligibleException extends EntityNotFoundException {

    static final long serialVersionUID = 1L;

    public StudentNotEligibleException(String message) {
        super(message);
    }
}
