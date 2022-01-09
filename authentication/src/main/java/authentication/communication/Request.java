package authentication.communication;

import authentication.entities.ResponseObj;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Request {

    private transient authentication.communication.SecurityContextHolder securityContextHolder;

    public Request() {}

    public Request(authentication.communication.SecurityContextHolder securityContextHolder) {
        this.securityContextHolder = securityContextHolder;
    }

    private static HttpClient client = HttpClient.newBuilder().build();

    public static void setClient(HttpClient client) {
        Request.client = client;
    }

    public static HttpClient getClient() {
        return client;
    }


    /**
     * Generic get method.
     *
     * @param url String with the url we want to GET
     * @return String with the responseBody
     */
    public ResponseObj get(String url) {
        securityContextHolder.setSecurityContext();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("netId", String.valueOf(this.securityContextHolder
                            .getSecurityContext().getAuthentication().getPrincipal()))
                    .GET()
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            ResponseObj responseObj = new ResponseObj(response.body(), response.statusCode());
            return responseObj;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseObj("error", 500);
        }
    }

    /**
     * Handles POST requests from the client.
     *
     * @param url that which the request is made
     * @return response body
     */
    public ResponseObj post(String url, String body) {
        try {
            HttpRequest request;
            if (body == null || body.length() == 0) {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .header("netId", String.valueOf(this.securityContextHolder
                                .getSecurityContext().getAuthentication().getPrincipal()))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .build();
            } else {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .header("netId", String.valueOf(this.securityContextHolder
                                .getSecurityContext().getAuthentication().getPrincipal()))
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();
            }

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            ResponseObj responseObj = new ResponseObj(response.body(), response.statusCode());
            return responseObj;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseObj("error", 500);
        }
    }

    /**
     * Handles PUT requests from the client.
     *
     * @param url that which the request is made
     * @return response body
     */
    public ResponseObj put(String url, String body) {
        try {
            HttpRequest request;
            if (body == null || body.length() == 0) {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .header("netId", String.valueOf(this.securityContextHolder
                                .getSecurityContext().getAuthentication().getPrincipal()))
                        .PUT(HttpRequest.BodyPublishers.noBody())
                        .build();
            } else {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Content-Type", "application/json")
                        .header("netId", String.valueOf(this.securityContextHolder
                                .getSecurityContext().getAuthentication().getPrincipal()))
                        .PUT(HttpRequest.BodyPublishers.ofString(body))
                        .build();
            }

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            ResponseObj responseObj = new ResponseObj(response.body(), response.statusCode());
            return responseObj;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseObj("error", 500);
        }
    }
}
