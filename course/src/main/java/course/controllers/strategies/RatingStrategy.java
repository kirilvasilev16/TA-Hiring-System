package course.controllers.strategies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import course.controllers.CourseController;
import course.entities.Course;
import course.entities.Management;
import course.entities.Student;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

public class RatingStrategy implements TARecommendationStrategy{
    private static HttpClient client = HttpClient.newBuilder().build();
    private static Gson gson = new GsonBuilder()
            .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
    private transient Course course;

    public RatingStrategy(Course course) {
        this.course = course;
    }

    /**
     * Sort students by rating
     * @param candidateTAs Set of Students
     * @return List sorted by rating
     */
    @SuppressWarnings("PMD")
    public List<String> getRecommendedTAs(Set<Student> candidateTAs){
        Map<Student, Float> studentRatingMap;
        studentRatingMap = new HashMap();
        HttpResponse<String> response;

        for(Student s : candidateTAs){
            float rating;
            HttpRequest request = HttpRequest.newBuilder().GET()
                    .uri(URI.create("http://localhost:8080/management/get?courseId=" + course.getCourseID() + "&studentId" + s.getNetId()))
                    .build();
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                e.printStackTrace();
                return List.of();
            }


            if (response.statusCode() != CourseController.successCode) {
                System.out.println("GET Status: " + response.statusCode());
            }
            System.out.println(response.body());
            rating = gson.fromJson(response.body(), Management.class).getRating();
            studentRatingMap.put(s, rating);
        }

        Comparator<Student> comparator = (Student s1, Student s2) -> studentRatingMap.get(s2) - studentRatingMap.get(s1) < 0 ? -1 : 1;
        return candidateTAs.stream().sorted(comparator).map(s -> s.getNetId()).collect(Collectors.toList());
    }
}
