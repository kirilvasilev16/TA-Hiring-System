package template.exceptions;

import javax.persistence.EntityNotFoundException;

public class CourseNotFoundException extends EntityNotFoundException {
	static final long serialVersionUID = 1L;
	public CourseNotFoundException(String message) {
		super(message);
	}
}