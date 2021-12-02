import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import template.entities.Lecturer;
import template.repositories.LecturerRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//public class LecturerRepositoryTest {
//    private final LecturerRepository lecturerRepository;
//    private transient Lecturer lecturer;
//
//    public LecturerRepositoryTest(LecturerRepository lecturerRepository) {
//        this.lecturerRepository = lecturerRepository;
//    }
//
//    @BeforeEach
//    void setUp() {
//        assertNotNull(lecturerRepository);
//        lecturer = new Lecturer("netId", "name", "password", "email", new ArrayList<>());
//        lecturerRepository.save(lecturer);
//    }
//
//    @Test
//    void getByNetId() {
//        assertEquals(lecturer, lecturerRepository.findLecturerByNetId("netId").get());
//    }
//}
