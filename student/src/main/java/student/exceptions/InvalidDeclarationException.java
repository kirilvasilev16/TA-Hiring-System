package student.exceptions;

public class InvalidDeclarationException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public InvalidDeclarationException(String message) {
        super(message);
    }
}
