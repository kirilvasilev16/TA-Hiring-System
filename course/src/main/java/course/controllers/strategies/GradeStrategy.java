package course.controllers.strategies;

import course.entities.Course;
import course.entities.Student;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GradeStrategy implements TaRecommendationStrategy {
    private transient Course course;

    public GradeStrategy(Course course) {
        this.course = course;
    }

    /**
     * Sort students by course grade.

     * @param candidateTas Set of Students
     * @return List sorted by course grade
     */
    @Override
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")//PMD comparator leaving scope for sorting
    public List<String> getRecommendedTas(Set<Student> candidateTas) {
        Comparator<Student> comparator = (o1, o2) -> {
            Float first = o1.getHighestGradeAchieved(course.getCourseId());
            Float second = o2.getHighestGradeAchieved(course.getCourseId());
            return -Float.compare(first, second);
        };

        return candidateTas.stream().sorted(comparator).map(s -> s.getNetId()).limit(10)
                .collect(Collectors.toList());
    }
}
