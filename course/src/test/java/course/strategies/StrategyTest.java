package course.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import course.controllers.strategies.ExperienceStrategy;
import course.controllers.strategies.GradeStrategy;
import course.controllers.strategies.RatingStrategy;
import course.controllers.strategies.TaRecommendationStrategy;
import course.entities.Course;
import course.entities.Student;
import course.exceptions.CourseNotPassedException;
import course.services.CommunicationService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StrategyTest {
    private transient Course course;
    private transient Set<String> lecturerSet;
    private transient LocalDateTime startingDate;
    private transient String courseId;
    private transient String courseName;
    private transient int courseSize;
    private transient int quarter;
    private transient String lecturer1 = "lecturer1";

    private transient Set<Student> candidateSet;

    @BeforeEach
    @SuppressWarnings("PMD")
    void setUp() {
        lecturerSet = new HashSet<>();
        lecturerSet.add(lecturer1);
        startingDate = LocalDateTime.of(LocalDate.of(2021, 11, 7), LocalTime.NOON);
        courseSize = 500;
        courseId = "CSE2115-2021";
        courseName = "SEM";
        quarter = 2;

        course = new Course(courseId, courseName, courseSize, lecturerSet, startingDate, quarter);

        candidateSet = new HashSet<>();

        Map<String, Float>[] passedCourses = new Map[]{
                Map.of(
                        "CSE2115-2020", 7.0f
                ), Map.of(
                "CSE2115-2020", 8.0f
        ), Map.of(
                "CSE2115-2020", 2.0f
        ), Map.of(
                "CSE2115-2020", 4.0f
        ), Map.of(
                "CSE2115-2020", 3.0f
        ), Map.of(
                "CSE2115-2020", 1.0f
        ), Map.of(
                "CSE2115-2020", 6.0f
        ), Map.of(
                "CSE2115-2020", 3.0f
        ), Map.of(
                "CSE2115-2020", 1.0f
        ), Map.of(
                "CSE2115-2020", 8.0f
        ), Map.of(
                "CSE2115-2019", 5.0f,
                "CSE2115-2020", 9.0f
        ), Map.of(
                "CSE2115-2020", 7.0f
        )
        };

        Set<String>[] taCourses = new Set[]{
            new HashSet(Arrays.asList("CSE1515", "CSE1005")),
            new HashSet(Arrays.asList("CSE1515")),
            new HashSet(Arrays.asList("CSE1515", "CSE1005")),
            new HashSet(Arrays.asList("CSE1515")),
            new HashSet(Arrays.asList("CSE1515", "CSE1005", "CSE1400")),
            new HashSet(Arrays.asList("CSE1515", "CSE1005")),
            new HashSet(Arrays.asList("CSE1515", "CSE1005")),
            new HashSet(Arrays.asList("CSE1515", "CSE1005")),
            new HashSet(Arrays.asList("CSE1515", "CSE1005")),
            new HashSet(Arrays.asList("CSE1515", "CSE1005")),
            new HashSet(Arrays.asList("CSE1515", "CSE1005")),
            new HashSet(Arrays.asList("CSE1515", "CSE1005")),
        };
        for (int i = 0; i < 12; i++) {
            Student s = new Student("Student" + (i + 1), passedCourses[i], taCourses[i]);
            candidateSet.add(s);
        }

    }

    @Test
    void sortByGrade() {
        TaRecommendationStrategy strategyImplementation = new GradeStrategy(course);
        List<String> result = strategyImplementation.getRecommendedTas(candidateSet);
        assertEquals("Student11", result.get(0));
        assertEquals(10, result.size());
        assertFalse(result.contains("Student6"));
        assertFalse(result.contains("Student9"));
    }

    @Test
    void noAvailableGrade() {
        TaRecommendationStrategy strategyImplementation = new GradeStrategy(course);
        candidateSet.add(new Student("Failure", Map.of(
                "CSE1225-2020", 3.0f),
                new HashSet(Arrays.asList("CSE1515", "CSE1005"))));
        assertThrows(CourseNotPassedException.class, () -> {
            strategyImplementation.getRecommendedTas(candidateSet);
        });
    }


    @Test
    void sortByExperience() {
        TaRecommendationStrategy strategyImplementation = new ExperienceStrategy();
        List<String> result = strategyImplementation.getRecommendedTas(candidateSet);
        assertEquals("Student5", result.get(0));
        assertEquals(10, result.size());
        assertFalse(result.contains("Student2"));
        assertFalse(result.contains("Student4"));
    }

    @Test
    @SuppressWarnings("PMD")
    void sortByRating() {
        Map<Student, Float> mockMap = new HashMap<>();
        float rating = 1f;
        String lastStudent = "Student0";
        for (Student s : candidateSet) {
            mockMap.put(s, rating);
            rating += 0.2;
            lastStudent = s.getNetId();
        }

        CommunicationService communicationService = Mockito.mock(CommunicationService.class);
        Mockito.when(communicationService.getRatings(Mockito.any(), Mockito.anyString()))
                .thenReturn(mockMap);

        TaRecommendationStrategy strategyImplementation =
                new RatingStrategy(course, communicationService);
        List<String> result = strategyImplementation.getRecommendedTas(candidateSet);
        assertEquals(10, result.size());
        assertEquals(lastStudent, result.get(0));

    }

}
