package management.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import management.entities.Hours;
import management.entities.Management;
import management.exceptions.InvalidApprovedHoursException;
import management.exceptions.InvalidContractHoursException;
import management.exceptions.InvalidIdException;
import management.exceptions.InvalidRatingException;
import management.repositories.ManagementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ManagementServiceTest {

    private transient ManagementRepository managementRepository;
    private transient ManagementService managementService;

    private transient Management management1;
    private transient Management management2;
    private transient List<Management> managements;
    private transient String courseId = "CSE1200";
    private transient String studentId = "aatanasov";

    @BeforeEach
    void setUp() {
        managementRepository = Mockito.mock(ManagementRepository.class);
        managementService = new ManagementService(managementRepository);

        management1 = new Management("CSE1200", "kvasilev", 120);
        management1.setId(1);
        management2 = new Management("CSE1200", "aatanasov", 70);
        management2.setId(2);
        managements = new ArrayList<>();
        managements.add(management1);
        managements.add(management2);

        Mockito.when(managementRepository.findAll()).thenReturn(managements);
        Mockito.when(managementRepository.getManagementByCourseAndStudent(courseId, studentId))
                .thenReturn(management2);
        Mockito.when(managementRepository.save(any(Management.class))).thenReturn(management2);
    }

    @Test
    void findAll() {
        List<Management> foundManagements = managementService.findAll();
        assertEquals(managements, foundManagements);
    }

    @Test
    void getOne() {
        Management foundManagement = managementService.getOne(courseId, studentId);
        assertEquals(management2, foundManagement);
    }

    @Test
    void getOneNull() {
        assertThrows(InvalidIdException.class,
                () -> managementService.getOne(courseId, "invalid"));
    }

    @Test
    void createManagement() {
        Management newManagement = managementService.createManagement("CSE1300", "bborisov", 70);
        assertEquals(management2, newManagement);
    }

    @Test
    void declareHoursValid() {
        managementService.declareHours(List.of(new Hours(courseId, studentId, 10)));

        assertEquals(10, management2.getDeclaredHours());
    }

    @Test
    void declareHoursInvalid() {
        assertThrows(InvalidContractHoursException.class,
                () -> managementService.declareHours(List.of(
                        new Hours(courseId, studentId, 1000))));
    }

    @Test
    void declareHoursInvalidNegative() {
        assertThrows(InvalidContractHoursException.class,
                () -> managementService.declareHours(List.of(
                        new Hours(courseId, studentId, -1000))));
    }


    @Test
    void approveHoursValid() {
        management2.setDeclaredHours(20);
        managementService.approveHours(courseId, studentId, 5);

        assertEquals(5, management2.getApprovedHours());
        assertEquals(15, management2.getDeclaredHours());
    }

    @Test
    void approveHoursInvalid() {
        assertThrows(InvalidApprovedHoursException.class,
                () -> managementService.approveHours(courseId, studentId, 1000));
    }

    @Test
    void approveHoursInvalidNegative() {
        assertThrows(InvalidApprovedHoursException.class,
                () -> managementService.approveHours(courseId, studentId, -10));
    }


    @Test
    void rateStudentValid() {
        managementService.rateStudent(courseId, studentId, 10);

        assertEquals(10, management2.getRating());
    }

    @Test
    void rateStudentInvalidPositive() {
        assertThrows(InvalidRatingException.class,
                () -> managementService.rateStudent(courseId, studentId, 11));
    }

    @Test
    void rateStudentInvalidNegative() {
        assertThrows(InvalidRatingException.class,
                () -> managementService.rateStudent(courseId, studentId, -1));
    }

    @Test
    void sendContract() {
        managementService.sendContract(courseId, studentId, "email@gmail.com");
        Mockito.verify(managementRepository, Mockito.only())
                .getManagementByCourseAndStudent(courseId, studentId);
    }

    @Test
    void sendContractInvalid() {
        assertThrows(InvalidIdException.class,
                () -> managementService.sendContract(courseId, "bad", "email@gmail.com"));
    }
}