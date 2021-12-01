package nl.tudelft.sem.student.services;

import nl.tudelft.sem.student.entities.Student;
import nl.tudelft.sem.student.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * The type Student service.
 */
@Service
public class StudentService {

    private final transient StudentRepository studentRepository;

    /**
     * Instantiates a new Student service.
     *
     * @param studentRepository the student repository
     */
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Gets student.
     *
     * @param id the id
     * @return the student
     */
    public Student getStudent(String id) {
        return studentRepository.getOne(id);
    }

    /**
     * Gets passed courses.
     *
     * @param id the id
     * @return the passed courses
     */
    public Map<String, Float> getPassedCourses(String id) {
        return studentRepository.getOne(id).getPassedCourses();
    }

    /**
     * Gets candidate courses.
     *
     * @param id the id
     * @return the candidate courses
     */
    public Map<String, Float> getCandidateCourses(String id) {
        return studentRepository.getOne(id).getCandidateCourses();
    }

    /**
     * Gets ta courses.
     *
     * @param id the id
     * @return the ta courses
     */
    public Map<String, Float> getTaCourses(String id) {
        return studentRepository.getOne(id).getTaCourses();
    }
}
