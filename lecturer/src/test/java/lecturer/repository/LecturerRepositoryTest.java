package lecturer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Optional;
import lecturer.entities.Lecturer;
import lecturer.repositories.LecturerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class LecturerRepositoryTest {
    @Autowired
    private transient LecturerRepository lecturerRepository;
    private transient Lecturer lecturer;

    @BeforeEach
    void setUp() {
        assertNotNull(lecturerRepository);
        lecturer = new Lecturer("netId", "name", "email", new ArrayList<>());
        lecturerRepository.save(lecturer);
    }

    @Test
    void getLecturer() {
        assertTrue(lecturerRepository.findAll().size() > 0);
        Optional<Lecturer> lecturer = lecturerRepository
                .findLecturerByNetId("netId");
        assertEquals(this.lecturer, lecturer.get());
    }
}
