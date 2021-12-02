package nl.tudelft.sem.student.config;

import java.util.HashSet;
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
            Student student1 = new Student("ohageman", "Ois");
            Set<String> c = new HashSet<>();
            c.add("c1");
            c.add("c2");
            Set<String> t = new HashSet<>();
            c.add("t1");
            c.add("t2");
            student1.setCandidateCourses(c);
            student1.setTaCourses(t);
            studentRepository.save(student1);
        };
    }
}
