package management.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import management.email.EmailSender;
import management.entities.Hours;
import management.entities.Management;
import management.exceptions.InvalidApprovedHoursException;
import management.exceptions.InvalidContractHoursException;
import management.exceptions.InvalidIdException;
import management.exceptions.InvalidRatingException;
import management.repositories.ManagementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ManagementServiceTest {

    private transient ManagementRepository managementRepository;
    private transient ManagementService managementService;
    private transient EmailSender emailSender;

    private transient Management management1;
    private transient Management management2;
    private transient List<Management> managements;
    private transient String courseId = "CSE1200";
    private transient String studentId = "aatanasov";
    private transient String studentId2 = "kvasilev";

    @BeforeEach
    void setUp() {
        managementRepository = Mockito.mock(ManagementRepository.class);
        emailSender = Mockito.mock(EmailSender.class);
        managementService = new ManagementService(managementRepository, emailSender);

        management1 = new Management(courseId, studentId2, 120);
        management1.setId(1);
        management2 = new Management(courseId, studentId, 70);
        management2.setId(2);
        management2.setRating(5);
        managements = new ArrayList<>();
        managements.add(management1);
        managements.add(management2);

        Mockito.when(managementRepository.findAll()).thenReturn(managements);
        Mockito.when(managementRepository.getManagementByCourseAndStudent(courseId, studentId))
                .thenReturn(management2);
        Mockito.when(managementRepository.save(any(Management.class))).thenReturn(management2);
        Mockito.when(managementRepository.getAverageTaRating(studentId))
                .thenReturn(management2.getRating());
        Mockito.when(managementRepository.getTaRecords(studentId)).thenReturn(1);
    }

    @Test
    void findAll() {
        List<Management> foundManagements = managementService.findAll();
        assertEquals(managements, foundManagements);

        Mockito.verify(managementRepository).findAll();
    }

    @Test
    void getOne() {
        Management foundManagement = managementService.getOne(courseId, studentId);
        assertEquals(management2, foundManagement);

        Mockito.verify(managementRepository)
                .getManagementByCourseAndStudent(courseId, studentId);
    }

    @Test
    void getAverageRating() {
        float rating = managementService.getAverageRating(studentId);
        assertEquals(management2.getRating(), rating);

        Mockito.verify(managementRepository).getTaRecords(studentId);
        Mockito.verify(managementRepository).getAverageTaRating(studentId);
    }

    @Test
    void getAverageRatingInvalid() {
        assertThrows(InvalidIdException.class,
                () -> managementService.getAverageRating("invalid"));

        Mockito.verify(managementRepository).getTaRecords("invalid");
    }

    @Test
    void getOneNull() {
        assertThrows(InvalidIdException.class,
                () -> managementService.getOne(courseId, "someone"));

        Mockito.verify(managementRepository)
                .getManagementByCourseAndStudent(courseId, "someone");
    }

    @Test
    void createManagement() {
        Management newManagement = managementService.createManagement("CSE1300", "bborisov", 70);
        assertEquals(management2, newManagement);

        Mockito.verify(managementRepository)
                .save(new Management("CSE1300", "bborisov", 70));
    }

    @Test
    void declareHoursValid() {
        managementService.declareHours(List.of(new Hours(courseId, studentId, 10)));

        Mockito.verify(managementRepository)
                .getManagementByCourseAndStudent(courseId, studentId);
        Mockito.verify(managementRepository).updateDeclaredHours(2, 10);

        assertEquals(10, management2.getDeclaredHours());
    }

    @Test
    void declareHoursZeroValid() {
        managementService.declareHours(List.of(new Hours(courseId, studentId, 0)));

        Mockito.verify(managementRepository)
                .getManagementByCourseAndStudent(courseId, studentId);
        Mockito.verify(managementRepository).updateDeclaredHours(2, 0);

        assertEquals(0, management2.getDeclaredHours());
    }

    @Test
    void declareHoursMultipleValid() {
        managementService.declareHours(List.of(new Hours(courseId, studentId, 10),
                new Hours(courseId, studentId, 50)));

        Mockito.verify(managementRepository, Mockito.times(2))
                .getManagementByCourseAndStudent(courseId, studentId);
        Mockito.verify(managementRepository).updateDeclaredHours(2, 10);
        Mockito.verify(managementRepository).updateDeclaredHours(2, 60);

        assertEquals(60, management2.getDeclaredHours());
    }

    @Test
    void declareHoursInvalid() {
        assertThrows(InvalidContractHoursException.class,
                () -> managementService.declareHours(List.of(
                        new Hours(courseId, studentId, 1000))));

        Mockito.verify(managementRepository)
                .getManagementByCourseAndStudent(courseId, studentId);
    }

    @Test
    void declareHoursInvalidOverDeclaration() {
        management2.setApprovedHours(60);
        managementService.declareHours(List.of(new Hours(courseId, studentId, 10)));


        assertThrows(InvalidContractHoursException.class,
                () -> managementService.declareHours(List.of(
                        new Hours(courseId, studentId, 30))));

        Mockito.verify(managementRepository, Mockito.times(2))
                .getManagementByCourseAndStudent(courseId, studentId);
        Mockito.verify(managementRepository)
                .updateDeclaredHours(2L, 10);
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

        Mockito.verify(managementRepository)
                .getManagementByCourseAndStudent(courseId, studentId);
        Mockito.verify(managementRepository).updateApprovedHours(2, 15, 5);

        assertEquals(5, management2.getApprovedHours());
        assertEquals(15, management2.getDeclaredHours());
    }

    @Test
    void approveHoursZeroValid() {
        managementService.approveHours(List.of(new Hours(courseId, studentId, 0)));

        Mockito.verify(managementRepository)
                .getManagementByCourseAndStudent(courseId, studentId);
        Mockito.verify(managementRepository).updateApprovedHours(2, 0, 0);

        assertEquals(0, management2.getApprovedHours());
    }

    @Test
    void approveHoursMultipleValid() {
        management2.setDeclaredHours(60);
        managementService.approveHours(List.of(new Hours(courseId, studentId, 10),
                new Hours(courseId, studentId, 50)));

        Mockito.verify(managementRepository, Mockito.times(2))
                .getManagementByCourseAndStudent(courseId, studentId);
        Mockito.verify(managementRepository).updateApprovedHours(2, 50, 10);
        Mockito.verify(managementRepository).updateApprovedHours(2, 0, 60);

        assertEquals(60, management2.getApprovedHours());
        assertEquals(0, management2.getDeclaredHours());
    }

    @Test
    void approveHoursInvalid() {
        assertThrows(InvalidApprovedHoursException.class,
                () -> managementService.approveHours(List.of(new Hours(courseId,
                        studentId, 1000))));

        Mockito.verify(managementRepository)
                .getManagementByCourseAndStudent(courseId, studentId);
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

        Mockito.verify(managementRepository)
                .getManagementByCourseAndStudent(courseId, studentId);
        Mockito.verify(managementRepository).updateRating(2, 7.8f);

        assertEquals(7.8f, management2.getRating());
    }

    @Test
    void rateStudentZeroValid() {
        managementService.rateStudent(courseId, studentId, 0);

        Mockito.verify(managementRepository)
                .getManagementByCourseAndStudent(courseId, studentId);
        Mockito.verify(managementRepository).updateRating(2, 0.0f);

        assertEquals(0, management2.getRating());
    }

    @Test
    void rateStudentTenValid() {
        managementService.rateStudent(courseId, studentId, 10);

        Mockito.verify(managementRepository)
                .getManagementByCourseAndStudent(courseId, studentId);
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

        Mockito.verify(managementRepository, Mockito.times(1))
                .getManagementByCourseAndStudent(courseId, studentId);
        Mockito.verify(emailSender).sendEmail("email@gmail.com", courseId, studentId, 70);
    }

    @Test
    void sendContractInvalid() {
        assertThrows(InvalidIdException.class,
                () -> managementService.sendContract(courseId, "bad", "email@gmail.com"));

        Mockito.verify(managementRepository)
                .getManagementByCourseAndStudent(courseId, "bad");
    }

    @AfterEach
    void cleanUp() {
        Mockito.verifyNoMoreInteractions(managementRepository);
        Mockito.verifyNoMoreInteractions(emailSender);
    }
}