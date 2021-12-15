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
    @SuppressWarnings("PMD")
    public List<String> getRecommendedTas(Set<Student> candidateTas) {
        Comparator<Student> comparator = new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                Float first = o1.getHighestGradeAchieved(course.getCourseId());
                Float second = o2.getHighestGradeAchieved(course.getCourseId());
                return  first > second ? -1 : 1;
            }
        };

        return candidateTas.stream().sorted(comparator).map(s -> s.getNetId()).limit(10)
                .collect(Collectors.toList());
    }
}
