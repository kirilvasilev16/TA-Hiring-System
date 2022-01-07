package student.services;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.communication.CourseCommunication;
import student.communication.ManagementCommunication;
import student.entities.Student;
import student.exceptions.InvalidDeclarationException;
import student.exceptions.StudentNotEligibleException;
import student.exceptions.StudentNotFoundException;
import student.repositories.StudentRepository;

/**
 * The type Student service.
 */
@Service
// PMD thinks student variable is not used, but since it is, we suppress the warning
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class StudentService {

    private final transient StudentRepository studentRepository;
    private final transient CourseCommunication courseCommunication;
    private final transient ManagementCommunication managementCommunication;

    /**
     * Instantiates a new Student service.
     *
     * @param studentRepository the student repository
     */
    @Autowired
    public StudentService(StudentRepository studentRepository,
                          CourseCommunication courseCommunication,
                          ManagementCommunication managementCommunication) {
        this.studentRepository = studentRepository;
        this.courseCommunication = courseCommunication;
        this.managementCommunication = managementCommunication;
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
            throw new StudentNotFoundException("Student with netId " + id + " was not found");
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
    public List<Student> getMultiple(List<String> ids) {
        List<Student> students = new ArrayList<>();
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
        if (student.getPassedCourses().containsKey(courseId.substring(0, courseId.lastIndexOf("-")))
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

    /**
     * Removes the student as candidate for a course.
     * and sends a request for the same to Course microservice as well.
     *
     * @param netId    the net id
     * @param courseId the course id
     * @return the student
     */
    public Student removeApplication(String netId, String courseId) {
        Student student = this.getStudent(netId);
        if (courseCommunication.removeAsCandidate(netId, courseId)) {
            Set<String> candidate = student.getCandidateCourses();
            candidate.remove(courseId);
            studentRepository.save(student);
        } else {
            throw new StudentNotEligibleException(
                    "Student is not a candidate yet for " + courseId);
        }
        return student;
    }

    /**
     * Sends a request to the Management microservice for declaring hours.
     *
     * @param json the json containing Hours data
     */
    public void declareHours(String json) {
        if (!managementCommunication.declareHours(json)) {
            throw new InvalidDeclarationException("Hours couldn't be declared");
        }
    }

    /**
     * Sends a request to the Course microservice for getting the average worked hours.
     *
     * @param courseId the course id
     * @return the the average worked hours for given course
     */
    public float averageWorkedHours(String courseId) {
        float avg = courseCommunication.averageWorkedHours(courseId);
        if (avg == -1) {
            throw new InvalidDeclarationException("Couldn't retrieve average worked hours");
        }
        return avg;
    }
}
