package authentication.communication;

import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.Security;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Request {

    private static final HttpClient client = HttpClient.newBuilder().build();

    /**
     * Generic get method.
     *
     * @param url String with the url we want to GET
     * @return String with the responseBody
     */
    public static String get(String url) throws IOException {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("netId", String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                    .GET()
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("response: " + response.body());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "err";

        }
    }

    /**
     * Handles POST requests from the client.
     *
     * @param url that which the request is made
     * @return response body
     */
    public static String post(String url, String body) throws InterruptedIOException {

        try {
            HttpRequest request;
            if (body == null || body.length() == 0) {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .header("netId", String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .build();
            } else {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .header("netId", String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();
            }

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            throw new InterruptedIOException();
        }
    }

    /**
     * Handles PUT requests from the client.
     *
     * @param url that which the request is made
     * @return response body
     */
    public static String put(String url, String body) throws InterruptedIOException {
        try {
            HttpRequest request;
            if (body == null || body.length() == 0) {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .header("netId", String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                        .PUT(HttpRequest.BodyPublishers.noBody())
                        .build();
            } else {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .header("netId", String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                        .PUT(HttpRequest.BodyPublishers.ofString(body))
                        .build();
            }

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            throw new InterruptedIOException();
        }
    }
}
