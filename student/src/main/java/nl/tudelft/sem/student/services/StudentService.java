package nl.tudelft.sem.student.services;

import nl.tudelft.sem.student.entities.Student;
import nl.tudelft.sem.student.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

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
    public Set<String> getCandidateCourses(String id) {
        return studentRepository.getOne(id).getCandidateCourses();
    }

    /**
     * Gets ta courses.
     *
     * @param id the id
     * @return the ta courses
     */
    public Set<String> getTaCourses(String id) {
        return studentRepository.getOne(id).getTaCourses();
    }

    public Student apply(String netId, String courseId) {
        Student student = studentRepository.getOne(netId);
        if (student == null) {
            return null;
        }
        if (student.getPassedCourses().containsKey(courseId)) {
            Set<String> candidate = student.getCandidateCourses();
            candidate.add(courseId);
            studentRepository.updateCandidateCourses(netId, candidate);
        }
        return student;
    }

    public Student accept(String netId, String courseId) {
        Student student = studentRepository.getOne(netId);
        if (student == null) {
            return null;
        }
        if (student.getCandidateCourses().contains(courseId)) {
            Set<String> candidate = student.getCandidateCourses();
            Set<String> ta = student.getTaCourses();
            candidate.remove(courseId);
            studentRepository.updateCandidateCourses(netId, candidate);
            ta.add(courseId);
            studentRepository.updateTaCourses(netId, ta);
        }
        return student;
    }
}
