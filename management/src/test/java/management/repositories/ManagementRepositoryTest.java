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

    @BeforeEach
    void setUp() {
        assertNotNull(managementRepository);
        managementRepository.save(new Management(101, 202, 120));
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
        assertEquals(0, management.getRating());

        managementRepository.updateRating(id, 5.0f);

        management = managementRepository.getOne(1L);
        assertEquals(5.0f, management.getRating());
    }
}