package nl.tudelft.sem.student.services;

import java.util.Map;
import java.util.Set;
import nl.tudelft.sem.student.entities.Student;
import nl.tudelft.sem.student.exceptions.StudentNotFoundException;
import nl.tudelft.sem.student.repositories.StudentRepository;
import org.springframework.stereotype.Service;

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
        Student student = studentRepository.getOne(id);
        if (student == null) {
            throw new StudentNotFoundException("");
        }
        return student;
    }

    /**
     * Gets passed courses.
     *
     * @param id the id
     * @return the passed courses
     */
    public Map<String, Float> getPassedCourses(String id) {
        return this.getStudent(id).getPassedCourses();
    }

    /**
     * Gets candidate courses.
     *
     * @param id the id
     * @return the candidate courses
     */
    public Set<String> getCandidateCourses(String id) {
        return this.getStudent(id).getCandidateCourses();
    }

    /**
     * Gets ta courses.
     *
     * @param id the id
     * @return the ta courses
     */
    public Set<String> getTaCourses(String id) {
        return this.getStudent(id).getTaCourses();
    }

    /**
     * Applies student for TA, if the student has passed the course.
     *
     * @param netId    the net id
     * @param courseId the course id
     * @return the student
     */
    public Student apply(String netId, String courseId) {
        Student student = this.getStudent(netId);
        if (student.getPassedCourses().containsKey(courseId)) {
            Set<String> candidate = student.getCandidateCourses();
            candidate.add(courseId);
            studentRepository.updateCandidateCourses(netId, candidate);
        }
        return student;
    }

    /**
     * Accepts student as TA for a course, if the student has applied before.
     *
     * @param netId    the net id
     * @param courseId the course id
     * @return the student
     */
    public Student accept(String netId, String courseId) {
        Student student = this.getStudent(netId);
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

    /**
     * Adds a student to the db.
     *
     * @param student the student
     * @return the student
     */
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }
}
