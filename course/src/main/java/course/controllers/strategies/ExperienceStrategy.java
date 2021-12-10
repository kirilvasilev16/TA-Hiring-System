package course.controllers.strategies;

import course.entities.Student;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExperienceStrategy implements TaRecommendationStrategy {

    /**
     * Sort students by experience.

     * @param candidateTas Set of Students
     * @return List sorted by experience
     */
    @Override
    public List<String> getRecommendedTas(Set<Student> candidateTas) {
        Comparator<Student> comparator = (Student s1, Student s2)
                -> s2.getTaCourses().size() - s1.getTaCourses().size();
        return candidateTas.stream().sorted(comparator).map(s -> s.getNetId()).limit(10)
                .collect(Collectors.toList());
    }
}
