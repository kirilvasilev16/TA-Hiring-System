package course.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import course.controllers.CourseController;
import course.entities.Management;
import course.entities.Student;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommunicationService {

    private static final HttpClient client = HttpClient.newBuilder().build();

    private static final Gson gson = new GsonBuilder().create();

    private static final String apiGatewayService = "http://localhost:8761";
    private static final String managementService = "http://localhost:8080/management";
    private static final String authenticationService = "http://localhost:8081/authentication";
    private static final String studentService = "http://localhost:8083/student";
    private static final String lecturerService = "http://localhost:8084/lecturer";

    public CommunicationService() {

    }

    /**
     * Gets the ratings of all the candidate TAs from the Management microservice.
     *
     * @param candidateTas the candidate tas
     * @param courseId     the course id
     * @return the ratings
     */
    public Map<Student, Float> getRatings(Set<Student> candidateTas, String courseId) {
        Map<Student, Float> studentRatingMap = new HashMap<>();
        HttpResponse<String> response;

        for (Student s : candidateTas) {
            HttpRequest request = HttpRequest.newBuilder().GET()
                    .uri(URI.create("http://localhost:8080/management/get?courseId="
                            + courseId + "&studentId" + s.getNetId()))
                    .build();
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                e.printStackTrace();
                return studentRatingMap;
            }


            if (response.statusCode() != CourseController.successCode) {
                System.out.println("GET Status: " + response.statusCode());
            }
            System.out.println(response.body());
            float rating;
            rating = gson.fromJson(response.body(), Management.class).getRating();
            studentRatingMap.put(s, rating);
        }

        return studentRatingMap;
    }

    /**
     * process send and receive of HTTP request and response body.
     *
     * @param request - HttpRequest body
     * @return True if response valid (200OK), false otherwise
     */
    public HttpResponse<String> requestSend(HttpRequest request) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Communication with server failed");
            return null; //TODO: proper exception handling
        }

    }

    /**
     * Request Management microservice to create new Management object.
     *
     * @param courseId String courseId
     * @param studentId String studentId
     * @param contractHours float contractHours
     * @return created Management object if successful else null
     */
    public Management createManagement(String courseId, String studentId, float contractHours) {

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(managementService + "/create?courseId=" + courseId + "&studentId="
                        + studentId + "&amountOfHours=" + contractHours)).build();

        HttpResponse<String> response = requestSend(request);

        if (response == null) {
            return null;
        }

        return gson.fromJson(response.body(), Management.class);
    }






}
