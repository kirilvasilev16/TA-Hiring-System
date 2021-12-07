package authentication.communication;

import authentication.entities.Management;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Request {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    private static final HttpClient client = HttpClient.newBuilder().build();
        /**
     * Generic get method.
     * @param url String with the url we want to GET
     * @return String with the responseBody
     */
    public static String get(String url) throws IOException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("response: " + response.body());
            return response.body();
        } catch (Exception e) {
            throw new InterruptedIOException();

        }
    }
    /**
     * Handles POST requests from the client.
     * @param url that which the request is made
     * @return response body
     */
    public static String post(String url) throws InterruptedIOException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e){
            throw new InterruptedIOException();
        }
    }

    /**
     * Handles PUT requests from the client.
     * @param url that which the request is made
     * @return response body
     */
    public static String put(String url) throws InterruptedIOException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e){
            throw new InterruptedIOException();
        }
    }

    /**
     * Handles DELETE requests from the client.
     * @param url that which the request is made
     * @param id id of the object stored in the database
     * @return deleted object
     */

//    /**
//     * Handles POST requests from the client.
//     * @param url that which the request is made
//     * @param t Json version of the object handled
//     * @return response body
//     */
//    public static <T> String post(String url, T t) {
//
//        return responseBody;
//    }
//
//    /**
//     * Handles PUT requests from the client.
//     * @param url that which the request is made
//     * @param t Json version of the object handled
//     * @return response body
//     */
//    public static <T> String put(String url, T t) {
//
//        return responseBody;
//    }
//
//    /**
//     * Handles DELETE requests from the client.
//     * @param url that which the request is made
//     * @param id id of the object stored in the database
//     * @return deleted object
//     */
//    public static String delete(String url, String id) {
//
//        return objects;
//    }

}
