package student.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import student.communication.CourseCommunication;
import student.communication.ManagementCommunication;
import student.entities.Student;
import student.exceptions.InvalidDeclarationException;
import student.exceptions.StudentNotEligibleException;
import student.exceptions.StudentNotFoundException;
import student.repositories.StudentRepository;


// PMD thinks couplingService variable is not used, but since it is, we suppress the warning
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class CouplingServiceTest {

    private transient Student student;
    private final transient String netId = "ohageman";
    private final transient String name = "Oisín";
    private final transient String courseCode = "CSE2115-2022";
    private final transient String json = "[{\n"
            + "    \"courseId\" : \"CSE2115-2022\",\n"
            + "    \"studentId\" : \"kvasilev\",\n"
            + "    \"hours\" : 20.0\n"
            + "}]";
    private transient Map<String, Float> passedCourses;
    private transient Set<String> candidateCourses;
    private transient Set<String> taCourses;
    private transient StudentRepository studentRepository; // mocked
    private transient CourseCommunication courseCommunication; // mocked
    private transient ManagementCommunication managementCommunication; // mocked
    private transient CouplingService couplingService; // not mocked
    private transient StudentService studentService; // mocked

    @BeforeEach
    void setUp() {
        passedCourses = new HashMap<>();
        passedCourses.put("CSE2115", 10.0f);
        passedCourses.put("CSE1400", 10.0f);
        passedCourses.put("CSE1105", 10.0f);
        student = new Student(netId, name);
        candidateCourses = new HashSet<>();
        candidateCourses.add(courseCode);
        taCourses = new HashSet<>();
        taCourses.add("CSE1105-2022");
        student.setPassedCourses(passedCourses);
        student.setCandidateCourses(candidateCourses);
        student.setTaCourses(taCourses);

        studentRepository = Mockito.mock(StudentRepository.class);
        courseCommunication = Mockito.mock(CourseCommunication.class);
        managementCommunication = Mockito.mock(ManagementCommunication.class);
        studentService = Mockito.mock(StudentService.class);
        couplingService = new CouplingService(
                studentRepository, courseCommunication, managementCommunication, studentService);
        Optional<Student> optionalStudent = Optional.of(student);

        Mockito.when(studentService.getStudent(netId)).thenReturn(student);
        Mockito.when(studentService.getStudent(not(eq(netId))))
                .thenThrow(new StudentNotFoundException(""));
        Mockito.when(courseCommunication.checkApplyRequirement(any(), any(), any()))
                .thenReturn(true);
        Mockito.when(courseCommunication.removeAsCandidate("ohageman", courseCode))
                .thenReturn(true);
        Mockito.when(courseCommunication.removeAsCandidate(eq("ohageman"), not(eq(courseCode))))
                .thenReturn(false);
        Mockito.when(managementCommunication.declareHours(json)).thenReturn(true);
        Mockito.when(managementCommunication.declareHours(not(eq(json)))).thenReturn(false);
    }

    @Test
    void applyTest() {
        Set<String> candidateCourses = new HashSet<>();
        candidateCourses.add(courseCode);
        candidateCourses.add("CSE1400-2021");
        Set<String> testCandidateCourses =
                couplingService.apply(netId, "CSE1400-2021").getCandidateCourses();
        assertEquals(candidateCourses, testCandidateCourses);
    }

    @Test
    void applyExceptionTest() {
        assertThrows(StudentNotEligibleException.class,
                () -> couplingService.apply(netId, "CSE9999-2022"));
    }

    @Test
    void removeApplicationTest() {
        Set<String> candidateCourses = new HashSet<>();
        Set<String> testCandidateCourses =
                couplingService.removeApplication(netId, courseCode).getCandidateCourses();
        assertEquals(candidateCourses, testCandidateCourses);
    }

    @Test
    void removeApplicationExceptionTest() {
        assertThrows(StudentNotEligibleException.class,
                () -> couplingService.removeApplication(netId, "CSE9999-2022"));
    }

    @Test
    void declareHoursTest() {
        assertDoesNotThrow(() ->
                couplingService.declareHours(json));
        Mockito.verify(managementCommunication).declareHours(any());
    }

    @Test
    void declareHoursExceptionTest() {
        assertThrows(InvalidDeclarationException.class, () ->
                couplingService.declareHours("notactuallyjson"));
    }

}
