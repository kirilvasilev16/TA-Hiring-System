package management.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import management.entities.Management;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
class ManagementRepositoryTest {

    @Autowired
    private transient ManagementRepository managementRepository;
    private transient Management management;
    private transient String kvasilev;

    @BeforeEach
    void setUp() {
        assertNotNull(managementRepository);
        kvasilev = "kvasilev";
        management = new Management("CSE1300", kvasilev, 60);
        management.setId(1);
        management.setRating(10);
        managementRepository.save(management);
        management = new Management("CSE1200", kvasilev, 120);
        management.setId(2);
        management.setRating(3);
        managementRepository.save(management);
    }

    @Test
    void getManagement() {
        assertTrue(managementRepository.findAll().size() > 0);
        Management management = managementRepository
                .getManagementByCourseAndStudent("CSE1200", kvasilev);

        assertEquals(this.management, management);
    }

    @Test
    void getAverageTaRating() {
        assertTrue(managementRepository.findAll().size() > 0);
        float rating = managementRepository
                .getAverageTaRating(kvasilev);

        assertEquals(6.5, rating);
    }

    @Test
    void getAverageNoRating() {
        managementRepository.deleteAll();
        management = new Management("CSE1500", kvasilev, 120);
        management.setId(1);
        managementRepository.save(management);

        assertTrue(managementRepository.findAll().size() > 0);
        float rating = managementRepository
                .getAverageTaRating(kvasilev);

        assertEquals(-1.0f, rating);
    }

    @Test
    void getAverageOneRating() {
        managementRepository.deleteAll();
        management = new Management("CSE1500", kvasilev, 120);
        management.setId(1);
        management.setRating(9);
        managementRepository.save(management);

        assertTrue(managementRepository.findAll().size() > 0);
        float rating = managementRepository
                .getAverageTaRating(kvasilev);

        assertEquals(9, rating);
    }

    @Test
    void getAverageTaRatingNonRatedCourse() {
        management = new Management("CSE1500", kvasilev, 120);
        management.setId(3);
        managementRepository.save(management);
        assertTrue(managementRepository.findAll().size() > 0);
        float rating = managementRepository
                .getAverageTaRating(kvasilev);

        assertEquals(6.5, rating);
    }

    @Test
    void getTaRecords() {
        assertTrue(managementRepository.findAll().size() > 0);
        int count = managementRepository
                .getTaRecords(kvasilev);

        assertEquals(2, count);
    }

    @Test
    void updateDeclaredHours() {
        assertTrue(managementRepository.findAll().size() > 0);
        long id = managementRepository.findAll().get(0).getId();
        Management management = managementRepository.getOne(id);
        assertEquals(0, management.getDeclaredHours());

        managementRepository.updateDeclaredHours(id, 20);

        management = managementRepository.getOne(id);
        assertEquals(20, management.getDeclaredHours());
    }

    @Test
    void updateApprovedHours() {
        assertTrue(managementRepository.findAll().size() > 0);
        long id = managementRepository.findAll().get(0).getId();
        Management management = managementRepository.getOne(id);
        assertEquals(0, management.getDeclaredHours());
        assertEquals(0, management.getApprovedHours());

        managementRepository.updateApprovedHours(id, 40, 30);

        management = managementRepository.getOne(id);
        assertEquals(40, management.getDeclaredHours());
        assertEquals(30, management.getApprovedHours());
    }

    @Test
    void updateRating() {
        assertTrue(managementRepository.findAll().size() > 0);
        long id = managementRepository.findAll().get(0).getId();
        Management management = managementRepository.getOne(id);
        assertEquals(10, management.getRating());

        managementRepository.updateRating(id, 5.0f);

        management = managementRepository.getOne(1L);
        assertEquals(5.0f, management.getRating());
    }
}