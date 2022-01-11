package student.services;


import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.communication.CourseCommunication;
import student.communication.ManagementCommunication;
import student.entities.Management;
import student.entities.Student;
import student.exceptions.InvalidDeclarationException;
import student.exceptions.StudentNotEligibleException;
import student.repositories.StudentRepository;

/**
 * The type Student service.
 */
@Service
// PMD thinks student variable is not used, but since it is, we suppress the warning
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class CouplingService {

    private final transient StudentRepository studentRepository;
    private final transient CourseCommunication courseCommunication;
    private final transient ManagementCommunication managementCommunication;
    private final transient StudentService studentService;

    /**
     * Instantiates a new CouplingService.
     *
     * @param studentRepository the student repository
     */
    @Autowired
    public CouplingService(StudentRepository studentRepository,
                           CourseCommunication courseCommunication,
                           ManagementCommunication managementCommunication,
                           StudentService studentService) {
        this.studentRepository = studentRepository;
        this.courseCommunication = courseCommunication;
        this.managementCommunication = managementCommunication;
        this.studentService = studentService;
    }

    /**
     * Applies student for TA, if the student has passed the course.
     *
     * @param netId    the net id
     * @param courseId the course id
     * @return the student
     */
    public Student apply(String netId, String courseId) {
        Student student = studentService.getStudent(netId);
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
     * Removes the student as candidate for a course.
     * and sends a request for the same to Course microservice as well.
     *
     * @param netId    the net id
     * @param courseId the course id
     * @return the student
     */
    public Student removeApplication(String netId, String courseId) {
        Student student = studentService.getStudent(netId);
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
     * @return the average worked hours for given course
     */
    public float averageWorkedHours(String courseId) {
        float avg = courseCommunication.averageWorkedHours(courseId);
        if (avg == -1) {
            throw new InvalidDeclarationException("Couldn't retrieve average worked hours");
        }
        return avg;
    }

    /**
     * Sends request to Management for getting all contract info for a student on a course.
     *
     * @param netId    the net id
     * @param courseId the course id
     * @return the Management object
     */
    public Management getManagement(String netId, String courseId) {
        return managementCommunication.getManagement(netId, courseId);
    }
}
