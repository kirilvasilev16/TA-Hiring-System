package nl.tudelft.sem.student.exceptions;

import javax.persistence.EntityNotFoundException;

public class StudentNotFoundException extends EntityNotFoundException {

    static final long serialVersionUID = 1L;

    public StudentNotFoundException(String message) {
        super(message);
    }
}
