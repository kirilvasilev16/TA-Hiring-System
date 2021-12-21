package authentication.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import authentication.entities.Role;
import authentication.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private transient RoleRepository roleRepository;
    private transient Role role = new Role("ROLE_admin");

    @BeforeEach
    void setUp() {
        assertNotNull(roleRepository);
        roleRepository.save(role);
    }

    @Test
    void getByNetId() {
        assertTrue(roleRepository.findAll().size() > 0);
        Role role = roleRepository
                .findByName("ROLE_admin");

        assertEquals(this.role, role);
    }
}
