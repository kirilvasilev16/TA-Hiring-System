package course.controllers.strategies;

import course.entities.Student;

import java.util.List;
import java.util.Set;

public interface TARecommendationStrategy {
    public List<String> getRecommendedTAs(Set<Student> candidateTAs);
}
