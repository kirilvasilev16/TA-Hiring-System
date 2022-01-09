package student.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Service;
import student.entities.Management;

// PMD thinks the response variables are never used, but since they are, we suppress the warning
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
@Service
public class ManagementCommunication {

    private transient HttpClient client;
    private transient Gson gson;

    public ManagementCommunication() {
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
     * Sends a request to the Management microservice for declaring hours.
     *
     * @param json the json containing Hours data
     * @return boolean whether the action has succeeded
     */
    public boolean declareHours(String json) {
        String uri = "http://localhost:8080/management/declareHours";
        HttpRequest request = HttpRequest.newBuilder().setHeader("Content-type",
                "application/json")
                .uri(URI.create(uri))
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
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
     * Sends request to Management for getting all contract info for a student on a course.
     *
     * @param netId    the net id
     * @param courseId the course id
     * @return the Management object
     */
    public Management getManagement(String netId, String courseId) {
        String uri = "http://localhost:8080/management/get?courseId="
                + courseId
                + "&studentId="
                + netId;
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
            return new Management();
        }
        return gson.fromJson(response.body(), Management.class);
    }
}
