package student.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.CommandLineRunner;
import student.entities.Student;
import student.repositories.StudentRepository;

public class DataConfigTest {

    private transient StudentRepository studentRepository;
    private transient DataConfig dataConfig;

    @BeforeEach
    void setUp() {
        dataConfig = new DataConfig();
        studentRepository = Mockito.mock(StudentRepository.class);
    }

    @Test
    void constructor() {
        assertNotNull(dataConfig);
    }

    @Test
    void commandLineRunnerTest() throws Exception {

        Map<String, Float> passed1 = new HashMap<>();
        passed1.put("CSE2115", 6.0f);
        passed1.put("CSE1400", 6.0f);
        passed1.put("CSE1105", 6.0f);
        Map<String, Float> passed2 = new HashMap<>();
        passed2.put("CSE2115", 8.0f);
        passed2.put("CSE1400", 8.0f);
        passed2.put("CSE1105", 8.0f);
        Map<String, Float> passed6 = new HashMap<>();
        passed6.put("CSE2115", 10.0f);
        passed6.put("CSE1400", 10.0f);
        passed6.put("CSE1105", 10.0f);
        String course1 = "CSE1105-2022";
        String course2 = "CSE1400-2021";
        String course3 = "CSE2115-2022";
        Set<String> candidate1 = new HashSet<>();
        candidate1.add(course1);
        candidate1.add(course2);
        Set<String> ta1 = new HashSet<>();
        ta1.add(course3);
        Student student1 = new Student("kvasilev", "Kiril");
        student1.setPassedCourses(passed1);
        student1.setCandidateCourses(candidate1);
        student1.setTaCourses(ta1);

        Set<String> candidate2 = new HashSet<>(candidate1);
        Set<String> ta2 = new HashSet<>(ta1);
        Student student2 = new Student("etrinh", "Eames");
        student2.setPassedCourses(passed2);
        student2.setCandidateCourses(candidate2);
        student2.setTaCourses(ta2);

        Set<String> candidate3 = new HashSet<>(ta1);
        Set<String> ta3 = new HashSet<>(candidate1);
        Student student3 = new Student("esozen", "Efe");
        Map<String, Float> passed3 = new HashMap<>(passed2);
        student3.setPassedCourses(passed3);
        student3.setCandidateCourses(candidate3);
        student3.setTaCourses(ta3);

        Set<String> candidate4 = new HashSet<>(ta1);
        Set<String> ta4 = new HashSet<>(candidate1);
        Student student4 = new Student("chengmouyeh", "Jeff");
        Map<String, Float> passed4 = new HashMap<>(passed2);
        student4.setPassedCourses(passed4);
        student4.setCandidateCourses(candidate4);
        student4.setTaCourses(ta4);

        Set<String> candidate5 = new HashSet<>(ta1);
        candidate5.add(course2);
        Set<String> ta5 = new HashSet<>();
        ta5.add(course1);
        Student student5 = new Student("akalandadze", "Anna");
        Map<String, Float> passed5 = new HashMap<>(passed2);
        student5.setPassedCourses(passed5);
        student5.setCandidateCourses(candidate5);
        student5.setTaCourses(ta5);

        Set<String> candidate6 = new HashSet<>(candidate5);
        Set<String> ta6 = new HashSet<>(ta5);
        Student student6 = new Student("ohageman", "Oisín");
        student6.setPassedCourses(passed6);
        student6.setCandidateCourses(candidate6);
        student6.setTaCourses(ta6);

        CommandLineRunner commandLineRunner
                = dataConfig.commandLineRunner(studentRepository);
        commandLineRunner.run();
        Mockito.verify(studentRepository).save(student1);
        Mockito.verify(studentRepository).save(student2);
        Mockito.verify(studentRepository).save(student3);
        Mockito.verify(studentRepository).save(student4);
        Mockito.verify(studentRepository).save(student5);
        Mockito.verify(studentRepository).save(student6);
    }
}
