package template.exceptions;

import javax.persistence.EntityNotFoundException;

public class LecturerNotFoundException extends EntityNotFoundException {
    static final long serialVersionUID = 1L;

    public LecturerNotFoundException(String message) {
        super(message);
    }
}
