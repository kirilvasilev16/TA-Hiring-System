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
    public List<String> getRecommendedTas(Set<Student> candidateTas) {
        Comparator<Student> comparator = (Student s1, Student s2) ->
                s2.getPassedCourses().get(course.getCourseId())
                        - s1.getPassedCourses().get(course.getCourseId()) < 0 ? -1 : 1;
        return candidateTas.stream().sorted(comparator).map(s -> s.getNetId())
                .collect(Collectors.toList());
    }
}
