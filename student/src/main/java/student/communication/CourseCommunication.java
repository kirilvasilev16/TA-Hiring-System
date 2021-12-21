package student.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CourseCommunication {

    private transient HttpClient client;
    private transient Gson gson;

    public CourseCommunication() {
        client = HttpClient.newBuilder().build();
        gson = new GsonBuilder().create();
    }

    /**
     * Sends a request to the course service, to apply a student for a course
     *
     * @param netId    the net id of the student
     * @param courseId the course id to apply for
     * @param set      the set of all courses that the student either is a TA for, or has applied for
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
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(set)))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            return false;
        }
        return response.statusCode() == 200;
    }
}
