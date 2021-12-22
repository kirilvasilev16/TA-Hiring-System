package student.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import student.entities.Student;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private transient StudentRepository studentRepository;
    private transient Student student;

    @BeforeEach
    void setUp() {
        Map<String, Float> passed = new HashMap<>();
        passed.put("CSE2115", 10.0f);
        passed.put("CSE1400", 10.0f);
        passed.put("CSE1105", 10.0f);
        student = new Student("ohageman", "Oisín");
        Set<String> candidate = new HashSet<>();
        candidate.add("CSE2115-2022");
        candidate.add("CSE1400-2021");
        Set<String> ta = new HashSet<>();
        candidate.add("CSE1105-2022");
        student.setCandidateCourses(candidate);
        student.setTaCourses(ta);
        studentRepository.save(student);
    }

    @Test
    void findStudentByNetIdTest() {
        assertEquals(Optional.of(student), studentRepository.findStudentByNetId("ohageman"));
    }

    @Test
    void findStudentByNetIdEmptyTest() {
        assertEquals(Optional.empty(), studentRepository.findStudentByNetId("no one"));
    }

}
