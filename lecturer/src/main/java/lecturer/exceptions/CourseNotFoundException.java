package lecturer.exceptions;

import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends EntityNotFoundException {
    static final long serialVersionUID = 1L;

    public CourseNotFoundException(String message) {
        super(message);
    }
}