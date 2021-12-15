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
    private transient String studentId2 = "kvasilev";

    @BeforeEach
    void setUp() {
        managementRepository = Mockito.mock(ManagementRepository.class);
        managementService = new ManagementService(managementRepository);

        management1 = new Management(courseId, studentId2, 120);
        management1.setId(1);
        management2 = new Management(courseId, studentId, 70);
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

        Mockito.verify(managementRepository).updateDeclaredHours(2, 10);

        assertEquals(10, management2.getDeclaredHours());
    }

    @Test
    void declareHoursZeroValid() {
        managementService.declareHours(List.of(new Hours(courseId, studentId, 0)));

        Mockito.verify(managementRepository).updateDeclaredHours(2, 0);

        assertEquals(0, management2.getDeclaredHours());
    }

    @Test
    void declareHoursMultipleValid() {
        managementService.declareHours(List.of(new Hours(courseId, studentId, 10),
                new Hours(courseId, studentId, 50)));

        Mockito.verify(managementRepository).updateDeclaredHours(2, 60);

        assertEquals(60, management2.getDeclaredHours());
    }

    @Test
    void declareHoursInvalid() {
        assertThrows(InvalidContractHoursException.class,
                () -> managementService.declareHours(List.of(
                        new Hours(courseId, studentId, 1000))));
    }

    @Test
    void declareHoursInvalidOverDeclaration() {
        management2.setApprovedHours(60);
        managementService.declareHours(List.of(new Hours(courseId, studentId, 10)));

        assertThrows(InvalidContractHoursException.class,
                () -> managementService.declareHours(List.of(
                        new Hours(courseId, studentId, 30))));
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
        managementService.approveHours(List.of(new Hours(courseId, studentId, 5)));

        Mockito.verify(managementRepository).updateApprovedHours(2, 15, 5);

        assertEquals(5, management2.getApprovedHours());
        assertEquals(15, management2.getDeclaredHours());
    }

    @Test
    void approveHoursZeroValid() {
        managementService.approveHours(List.of(new Hours(courseId, studentId, 0)));

        Mockito.verify(managementRepository).updateApprovedHours(2, 0, 0);

        assertEquals(0, management2.getApprovedHours());
    }

    @Test
    void approveHoursMultipleValid() {
        management2.setDeclaredHours(60);
        managementService.approveHours(List.of(new Hours(courseId, studentId, 10),
                new Hours(courseId, studentId, 50)));

        Mockito.verify(managementRepository).updateApprovedHours(2, 0, 60);

        assertEquals(60, management2.getApprovedHours());
        assertEquals(0, management2.getDeclaredHours());
    }

    @Test
    void approveHoursInvalid() {
        assertThrows(InvalidApprovedHoursException.class,
                () -> managementService.approveHours(List.of(new Hours(courseId,
                        studentId, 1000))));
    }

    @Test
    void approveHoursInvalidNegative() {
        assertThrows(InvalidApprovedHoursException.class,
                () -> managementService.approveHours(List.of(new Hours(courseId,
                        studentId, -10))));
    }


    @Test
    void rateStudentValid() {
        managementService.rateStudent(courseId, studentId, 7.8f);

        Mockito.verify(managementRepository).updateRating(2, 7.8f);

        assertEquals(7.8f, management2.getRating());
    }

    @Test
    void rateStudentZeroValid() {
        managementService.rateStudent(courseId, studentId, 0);

        Mockito.verify(managementRepository).updateRating(2, 0.0f);

        assertEquals(0, management2.getRating());
    }

    @Test
    void rateStudentTenValid() {
        managementService.rateStudent(courseId, studentId, 10);

        Mockito.verify(managementRepository).updateRating(2, 10.0f);

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