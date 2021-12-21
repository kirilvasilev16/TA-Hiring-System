package student.config;

import student.entities.Student;
import student.repositories.StudentRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
            Map<String, Float> passed1 = new HashMap<>();
            passed1.put("CSE2115", 6.0f);
            passed1.put("CSE1400", 6.0f);
            passed1.put("CSE1105", 6.0f);
            Map<String, Float> passed2 = new HashMap<>();
            passed2.put("CSE2115", 8.0f);
            passed2.put("CSE1400", 8.0f);
            passed2.put("CSE1105", 8.0f);
            Map<String, Float> passed3 = new HashMap<>(passed2);
            Map<String, Float> passed4 = new HashMap<>(passed2);
            Map<String, Float> passed5 = new HashMap<>(passed2);
            Map<String, Float> passed6 = new HashMap<>();
            passed3.put("CSE2115", 10.0f);
            passed3.put("CSE1400", 10.0f);
            passed3.put("CSE1105", 10.0f);
            String course1 = "CSE1105-2022";
            String course2 = "CSE1400-2021";
            String course3 = "CSE2115-2022";
            Set<String> candidate1 = new HashSet<>();
            candidate1.add(course1);
            candidate1.add(course2);
            Set<String> ta1 = new HashSet<>();
            ta1.add(course3);
            Set<String> candidate2 = new HashSet<>(candidate1);
            Set<String> ta2 = new HashSet<>(ta1);
            Set<String> candidate3 = new HashSet<>(ta1);
            Set<String> ta3 = new HashSet<>(candidate1);
            Set<String> candidate4 = new HashSet<>(ta1);
            Set<String> ta4 = new HashSet<>(candidate1);
            Set<String> candidate5 = new HashSet<>(ta1);
            candidate5.add(course2);
            Set<String> ta5 = new HashSet<>();
            ta5.add(course1);
            Set<String> candidate6 = new HashSet<>(candidate5);
            Set<String> ta6 = new HashSet<>(ta5);

            Student student1 = new Student("kvasilev", "Kiril");
            student1.setPassedCourses(passed1);
            student1.setCandidateCourses(candidate1);
            student1.setTaCourses(ta1);
            Student student2 = new Student("etrinh", "Eames");
            student2.setPassedCourses(passed2);
            student2.setCandidateCourses(candidate2);
            student2.setTaCourses(ta2);
            Student student3 = new Student("esozen", "Efe");
            student3.setPassedCourses(passed3);
            student3.setCandidateCourses(candidate3);
            student3.setTaCourses(ta3);
            Student student4 = new Student("chengmouyeh", "Jeff");
            student4.setPassedCourses(passed4);
            student4.setCandidateCourses(candidate4);
            student4.setTaCourses(ta4);
            Student student5 = new Student("akalandadze", "Anna");
            student5.setPassedCourses(passed5);
            student5.setCandidateCourses(candidate5);
            student5.setTaCourses(ta5);
            Student student6 = new Student("ohageman", "Oisín");
            student6.setPassedCourses(passed6);
            student6.setCandidateCourses(candidate6);
            student6.setTaCourses(ta6);
            studentRepository.save(student1);
            studentRepository.save(student2);
            studentRepository.save(student3);
            studentRepository.save(student4);
            studentRepository.save(student5);
            studentRepository.save(student6);
        };
    }
}
