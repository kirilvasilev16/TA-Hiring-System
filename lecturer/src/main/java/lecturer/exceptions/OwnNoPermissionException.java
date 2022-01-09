package lecturer.exceptions;

import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class OwnNoPermissionException extends PermissionDeniedDataAccessException {
    static final long serialVersionUID = 1L;

    public OwnNoPermissionException(String message) {
        super(message, new Throwable());
    }
}
