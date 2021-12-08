package course.controllers.strategies;

import course.entities.Student;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExperienceStrategy implements TARecommendationStrategy{

    /**
     * Sort students by experience
     * @param candidateTAs Set of Students
     * @return List sorted by experience
     */
    @Override
    public List<String> getRecommendedTAs(Set<Student> candidateTAs){
        Comparator<Student> comparator = (Student s1, Student s2) -> s2.getTaCourses().size() - s1.getTaCourses().size();
        return candidateTAs.stream().sorted(comparator).map(s -> s.getNetId()).collect(Collectors.toList());
    }
}
