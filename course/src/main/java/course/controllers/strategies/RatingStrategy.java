package course.controllers.strategies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import course.entities.Course;
import course.entities.Student;
import course.services.CommunicationService;
import java.net.http.HttpClient;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RatingStrategy implements TaRecommendationStrategy {
    private static HttpClient client = HttpClient.newBuilder().build();
    private static Gson gson = new GsonBuilder()
            .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
    private transient Course course;
    private transient CommunicationService communicationService;

    public RatingStrategy(Course course, CommunicationService communicationService) {
        this.course = course;
        this.communicationService = communicationService;
    }

    /**
     * Sort students by rating.

     * @param candidateTas Set of Students
     * @return List sorted by rating
     */
    @SuppressWarnings("PMD")
    public List<String> getRecommendedTas(Set<Student> candidateTas) {
        Map<Student, Float> studentRatingMap =
                communicationService.getRatings(candidateTas, course.getCourseId());

        Comparator<Student> comparator = (Student s1, Student s2)
                -> studentRatingMap.get(s2) - studentRatingMap.get(s1) < 0 ? -1 : 1;
        return candidateTas.stream().sorted(comparator).map(s -> s.getNetId()).limit(10)
                .collect(Collectors.toList());
    }
}
