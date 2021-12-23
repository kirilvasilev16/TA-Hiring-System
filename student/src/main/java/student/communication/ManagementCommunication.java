package student.communication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.stereotype.Service;

// PMD thinks the response variables are never used, but since they are, we suppress the warning
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
@Service
public class ManagementCommunication {

    private transient HttpClient client;

    public ManagementCommunication() {
        client = HttpClient.newBuilder().build();
    }


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
}
