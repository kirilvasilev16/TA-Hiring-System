package course.controllers.strategies;

import course.entities.Student;
import java.util.List;
import java.util.Set;

public interface TaRecommendationStrategy {
    public List<String> getRecommendedTas(Set<Student> candidateTas);
}
