package nl.tudelft.sem.student.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nl.tudelft.sem.student.entities.Student;
import nl.tudelft.sem.student.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
Sets up some mock data for manual testing
 */
@Configuration
public class DataConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Map<String, Float> passed = new HashMap<>();
            passed.put("cse1000", 7.0f);
            passed.put("cse2000", 8.0f);
            passed.put("cse3000", 6.0f);
            passed.put("cse4000", 7.0f);
            Set<String> candidate = new HashSet<>();
            candidate.add("cse2000");
            Set<String> ta = new HashSet<>();
            ta.add("cse4000");
            Student student1 = new Student("ohageman", "Ois");
            student1.setPassedCourses(passed);
            student1.setCandidateCourses(candidate);
            student1.setTaCourses(ta);
            studentRepository.save(student1);
        };
    }
}
