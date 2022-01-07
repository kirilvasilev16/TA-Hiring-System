package course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 42L;

    public CourseNotFoundException(String code) {
        super("Could not find a course with id " + code);
    }
}
