package student.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import org.springframework.stereotype.Service;

// PMD thinks the response variables are never used, but since they are, we suppress the warning
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
@Service
public class CourseCommunication {

    private transient HttpClient client;
    private transient Gson gson;

    public CourseCommunication() {
        client = HttpClient.newBuilder().build();
        gson = new GsonBuilder().create();
    }

    /**
     * Sets HttpClient.
     *
     * @param client the client
     */
    public void setClient(HttpClient client) {
        this.client = client;
    }

    /**
     * Send request and checks whether it was successful
     *
     * @param request the http request
     * @return the boolean
     */
    public boolean sendRequest(HttpRequest request) {
        HttpResponse<String> response;
        try {
            // PMD DU anomaly
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return false;
        }
        return response.statusCode() == 200;
    }

    /**
     * Sends a request to the course service, to apply a student for a course.
     *
     * @param netId    the net id of the student
     * @param courseId the course id to apply for
     * @param set      the set of all courses that the student either is a TA for, or applied for
     * @return the boolean
     */
    public boolean checkApplyRequirement(String netId, String courseId, Set<String> set) {
        String uri = "http://localhost:8082/courses/addCandidateTa?courseId="
                + courseId
                + "&studentId="
                + netId;
        HttpRequest request = HttpRequest.newBuilder().setHeader("Content-type",
                "application/json")
                .uri(URI.create(uri))
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(set)))
                .build();
        return sendRequest(request);
    }

    /**
     * Sends a request to Course microservice for removing a student as ta candidate.
     *
     * @param netId    the net id
     * @param courseId the course id
     * @return boolean whether the action has succeeded
     */
    public boolean removeAsCandidate(String netId, String courseId) {
        String uri = "http://localhost:8082/courses/removeAsCandidate?courseId="
                + courseId
                + "&studentId="
                + netId;
        HttpRequest request = HttpRequest.newBuilder().setHeader("Content-type",
                "application/json")
                .uri(URI.create(uri))
                .DELETE()
                .build();
        return sendRequest(request);
    }

    /**
     * Sends a request to Course microservice for retrieving the average worked hours.
     *
     * @param courseId the course id
     * @return the average worked hours for the given course, or -1 if request failed
     */
    public float averageWorkedHours(String courseId) {
        String uri = "http://localhost:8082/courses/averageWorkedHours?courseId="
                + courseId;
        HttpRequest request = HttpRequest.newBuilder().setHeader("Content-type",
                "application/json")
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            // PMD DU anomaly
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return -1;
        }
        return gson.fromJson(response.body(), Float.class);
    }
}
