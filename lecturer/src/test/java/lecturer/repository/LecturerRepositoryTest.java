//package lecturer.repository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import java.util.ArrayList;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import lecturer.entities.Lecturer;
//import lecturer.repositories.LecturerRepository;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//public class LecturerRepositoryTest {
//    private final transient LecturerRepository lecturerRepository;
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
//}
