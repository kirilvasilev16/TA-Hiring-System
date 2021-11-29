package management.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
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
        Mockito.when(managementRepository.getOne(2L)).thenReturn(management2);
        Mockito.when(managementRepository.save(any(Management.class))).thenReturn(management2);
    }

    @Test
    void findAll() {
        List<Management> foundManagements = managementService.findAll();
        assertEquals(managements, foundManagements);
    }

    @Test
    void getOne() {
        Management foundManagement = managementService.getOne(2L);
        assertEquals(management2, foundManagement);
    }

    @Test
    void createManagement() {
        Management newManagement = managementService.createManagement("CSE1300", "bborisov", 70);
        assertEquals(management2, newManagement);
    }

    @Test
    void declareHoursValid() {
        managementService.declareHours(2, 10);

        assertEquals(10, management2.getDeclaredHours());
    }

    @Test
    void declareHoursInvalid() {
        assertThrows(InvalidContractHoursException.class,
                () -> managementService.declareHours(2, 1000));
    }

    @Test
    void declareHoursInvalidNegative() {
        assertThrows(InvalidContractHoursException.class,
                () -> managementService.declareHours(2, -10));
    }

    @Test
    void declareHoursNull() {
        assertThrows(InvalidIdException.class,
                () -> managementService.declareHours(3, -10));
    }

    @Test
    void approveHoursValid() {
        management2.setDeclaredHours(20);
        managementService.approveHours(2, 5);

        assertEquals(5, management2.getApprovedHours());
        assertEquals(15, management2.getDeclaredHours());
    }

    @Test
    void approveHoursInvalid() {
        assertThrows(InvalidApprovedHoursException.class,
                () -> managementService.approveHours(2, 1000));
    }

    @Test
    void approveHoursInvalidNegative() {
        assertThrows(InvalidApprovedHoursException.class,
                () -> managementService.approveHours(2, -10));
    }

    @Test
    void approveHoursNull() {
        assertThrows(InvalidIdException.class,
                () -> managementService.approveHours(3, -10));
    }

    @Test
    void rateStudentValid() {
        managementService.rateStudent(2, 10);

        assertEquals(10, management2.getRating());
    }

    @Test
    void rateStudentInvalidPositive() {
        assertThrows(InvalidRatingException.class,
                () -> managementService.rateStudent(2, 11));
    }

    @Test
    void rateStudentInvalidNegative() {
        assertThrows(InvalidRatingException.class,
                () -> managementService.rateStudent(2, -1));
    }

    @Test
    void rateStudentNull() {
        assertThrows(InvalidIdException.class,
                () -> managementService.rateStudent(3, -1));
    }

    @Test
    void sendContract() {
        managementService.sendContract(2, "email@gmail.com");
        Mockito.verify(managementRepository, Mockito.only()).getOne(2L);
    }

    @Test
    void sendContractInvalid() {
        assertThrows(InvalidIdException.class,
                () -> managementService.sendContract(3, "email@gmail.com"));
    }
}