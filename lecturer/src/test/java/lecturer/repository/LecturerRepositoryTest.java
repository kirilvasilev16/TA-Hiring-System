package lecturer.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import lecturer.entities.Lecturer;
import lecturer.repositories.LecturerRepository;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class LecturerRepositoryTest {
    @Autowired
    private transient LecturerRepository lecturerRepository;
    private transient Lecturer lecturer;

    @BeforeEach
    void setUp() {
        assertNotNull(lecturerRepository);
        lecturer = new Lecturer("netId", "name", "password", "email", new ArrayList<>());
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
