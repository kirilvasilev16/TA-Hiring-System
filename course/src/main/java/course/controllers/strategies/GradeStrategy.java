package course.controllers.strategies;

import course.entities.Course;
import course.entities.Student;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GradeStrategy implements TARecommendationStrategy{
    private Course course;

    public GradeStrategy(Course course) {
        this.course = course;
    }

    /**
     * Sort students by course grade
     * @param candidateTAs Set of Students
     * @return List sorted by course grade
     */
    @Override
    public List<String> getRecommendedTAs(Set<Student> candidateTAs){
        Comparator<Student> comparator = (Student s1, Student s2) ->
                s2.getPassedCourses().get(course.getCourseID())
                        - s1.getPassedCourses().get(course.getCourseID()) < 0 ? -1 : 1;
        return candidateTAs.stream().sorted(comparator).map(s -> s.getNetId()).collect(Collectors.toList());
    }
}
