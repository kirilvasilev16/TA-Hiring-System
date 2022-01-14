package course.controllers.strategies;

import course.entities.Course;
import course.entities.Student;
import course.exceptions.InvalidStrategyException;
import course.services.CommunicationService;
import java.util.List;
import java.util.Set;

public interface TaRecommendationStrategy {
    public List<String> getRecommendedTas(Set<Student> candidateTas);

    /**
     *  Retrieves right strategy based on string.
     *
     * @param strategy             Choose from "rating", "experience" or "grade"
     * @param communicationService CommunicationService object
     * @param course               Course object
     * @return                     Specific instance of Strategy
     */
    public static TaRecommendationStrategy getRecommendationType(String strategy,
           CommunicationService communicationService,
           Course course) throws InvalidStrategyException {
        TaRecommendationStrategy strategyImplementation;
        if (strategy.equals("rating")) {
            strategyImplementation = new RatingStrategy(course, communicationService);
        } else if (strategy.equals("experience")) {
            strategyImplementation = new ExperienceStrategy();
        } else if (strategy.equals("grade")) {
            strategyImplementation = new GradeStrategy(course);
        } else {
            throw new InvalidStrategyException(strategy + " is not a valid strategy");
        }
        return strategyImplementation;
    }
}
