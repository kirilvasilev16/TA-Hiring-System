package student.services;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import student.communication.CourseCommunication;
import student.entities.Student;
import student.exceptions.StudentNotEligibleException;
import student.exceptions.StudentNotFoundException;
import student.repositories.StudentRepository;

/**
 * The type Student service.
 */
@Service
public class StudentService {

    private final transient StudentRepository studentRepository;
    private final transient CourseCommunication courseCommunication;

    /**
     * Instantiates a new Student service.
     *
     * @param studentRepository the student repository
     */
    public StudentService(StudentRepository studentRepository,
                          CourseCommunication courseCommunication) {
        this.studentRepository = studentRepository;
        this.courseCommunication = courseCommunication;
    }

    /**
     * Gets student.
     *
     * @param id the id
     * @return the student
     */
    public Student getStudent(String id) {
        Optional<Student> student = studentRepository.findStudentByNetId(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException("");
        }
        return student.get();
    }

    /**
     * Gets all students in db.
     *
     * @return list of all students
     */
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    /**
     * Gets all students corresponding to given ids.
     * Used by course microservice.
     *
     * @param ids the ids
     * @return set of students
     */
    public Set<Student> getMultiple(Set<String> ids) {
        Set<Student> students = new HashSet<>();
        for (String id : ids) {
            students.add(this.getStudent(id));
        }
        return students;
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
        Set<String> totalSet = new HashSet<>(student.getTaCourses());
        totalSet.addAll(student.getCandidateCourses());
        if (student.getPassedCourses().containsKey(courseId.substring(0, 7))
                && courseCommunication.checkApplyRequirement(netId, courseId, totalSet)) {
            Set<String> candidate = student.getCandidateCourses();
            candidate.add(courseId);
            studentRepository.save(student);
        } else {
            throw new StudentNotEligibleException(
                    "Student is not eligible for applying to course " + courseId);
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
            ta.add(courseId);
            studentRepository.save(student);
        } else {
            throw new StudentNotEligibleException("Student has not applied to course " + courseId);
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
