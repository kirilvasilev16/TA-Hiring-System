package nl.tudelft.sem.student.exceptions;

import javax.persistence.EntityNotFoundException;

public class StudentNotEligibleException extends EntityNotFoundException {

    static final long serialVersionUID = 1L;

    public StudentNotEligibleException(String message) {
        super(message);
    }
}
