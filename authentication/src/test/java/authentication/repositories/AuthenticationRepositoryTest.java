package authentication.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import authentication.entities.Authentication;
import authentication.entities.Role;
import authentication.repository.AuthenticationRepository;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class AuthenticationRepositoryTest {

    @Autowired
    private transient AuthenticationRepository authenticationRepository;
    private transient Role role = new Role("ROLE_admin");
    private transient Authentication authentication =
            new Authentication("mrhug", "email", "mrhug123",
            "stefan",  new ArrayList<Role>(
            Arrays.asList(role)
    ));

    @BeforeEach
    void setUp() {
        assertNotNull(authenticationRepository);
        authenticationRepository.save(authentication);
    }

    @Test
    void getByNetId() {
        assertTrue(authenticationRepository.findAll().size() > 0);
        Authentication authentication = authenticationRepository
                .findByNetId("mrhug");

        assertEquals(this.authentication.getNetId(), authentication.getNetId());
    }
}
