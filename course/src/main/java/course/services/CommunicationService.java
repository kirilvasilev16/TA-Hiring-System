package course.services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import course.entities.Management;
import course.entities.Student;
import course.exceptions.FailedContractCreationException;
import course.exceptions.FailedGetHoursException;
import course.exceptions.FailedGetStudentListException;
import course.exceptions.FailedGetStudentRatingsException;
import course.exceptions.FailedUpdateStudentEmploymentException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CommunicationService {

    private static HttpClient client = HttpClient.newBuilder().build();

    private static final Gson gson = new GsonBuilder().create();

    public static int successCode = 200;

    private static final String apiGatewayService = "http://localhost:8761";
    private static final String managementService = "http://localhost:8080/management";
    private static final String authenticationService = "http://localhost:8081/authentication";
    private static final String studentService = "http://localhost:8083/student";
    private static final String lecturerService = "http://localhost:8084/lecturer";

    public CommunicationService() {

    }

    public static void setClient(HttpClient client) {
        CommunicationService.client = client;
    }

    public static HttpClient getClient() {
        return client;
    }

    /**
     * Gets the ratings of all the candidate TAs from the Management microservice.
     *
     * @param candidateTas the candidate tas
     * @param courseId     the course id
     * @return the ratings
     */
    @SuppressWarnings("PMD")
    public Map<Student, Float> getRatings(Set<Student> candidateTas, String courseId) {
        Map<Student, Float> studentRatingMap = new HashMap<>();
        HttpResponse<String> response;

        for (Student s : candidateTas) {
            HttpRequest request = HttpRequest.newBuilder().GET()
                    .uri(URI.create(managementService + "/getAverageRating?"
                            + "studentId=" + s.getNetId())).build();
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                throw new FailedGetStudentRatingsException(
                        "Failed to get " + s.getNetId() + " ratings");
            }

            if (response.statusCode() != successCode) {
                System.out.println("GET Status: " + response.statusCode());
            }
            System.out.println(response.body());
            float rating;
            rating = gson.fromJson(response.body(), Float.class);
            studentRatingMap.put(s, rating);
        }

        return studentRatingMap;
    }

    /**
     * Request Management microservice to create new Management object.
     *
     * @param courseId      String courseId
     * @param studentId     String studentId
     * @param contractHours float contractHours
     * @return created Management object if successful else null
     * @throws FailedContractCreationException if request to Management microservice fails
     */
    @SuppressWarnings("PMD")
    public Management createManagement(String courseId, String studentId, float contractHours) {

        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(managementService + "/create?courseId=" + courseId + "&studentId="
                        + studentId + "&amountOfHours=" + contractHours)).build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new FailedContractCreationException("Could not create " + courseId
                    + " TA Work Contract for " + studentId);
        }

        return gson.fromJson(response.body(), Management.class);
    }


    /**
     * Gets full list of students from the Student microservice.
     *
     * @param candidateTas the candidate ta strings
     * @return the students
     */
    @SuppressWarnings("PMD")
    public Set<Student> getStudents(Set<String> candidateTas) {
        Set<Student> students = new HashSet<>();
        for (String studentId : candidateTas) {
            HttpRequest request = HttpRequest.newBuilder().GET()
                    .uri(URI.create(studentService + "/get?id=" + studentId))
                    .build();
            HttpResponse<String> response;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                throw new FailedGetStudentListException("Failed to get student " + studentId);
            }

            if (response.statusCode() != successCode) {
                System.out.println("GET Status: " + response.statusCode());
            }
            System.out.println(response.body());
            students.add(gson.fromJson(response.body(), Student.class));
        }
        return students;
    }

    /**
     * Gets list of hours from the Management microservice.
     *
     * @param hiredTas the hired tas
     * @param courseId the course id
     * @return the hours list
     */
    @SuppressWarnings("PMD")
    public Set<Float> getHoursList(Set<String> hiredTas, String courseId) {
        Set<Float> hourSet = new HashSet<>();

        for (String ta : hiredTas) {
            HttpRequest request = HttpRequest.newBuilder().GET()
                    .uri(URI.create(managementService + "/getAmountOfHours?courseId="
                            + courseId + "&studentId=" + ta))
                    .build();
            HttpResponse<String> response;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                throw new FailedGetHoursException("Failed to get worked hours for student " + ta);
            }

            if (response.statusCode() != successCode) {
                System.out.println("GET Status: " + response.statusCode());
            }
            System.out.println(response.body());
            hourSet.add(gson.fromJson(response.body(), Float.class));
        }
        return hourSet;
    }

    /**
     * Update Student microservice on employment of student.
     *
     * @param studentId String student netId
     * @param courseId  String courseId
     * @return true is update successful
     * @throws FailedUpdateStudentEmploymentException if updating Student microservice fails
     */
    @SuppressWarnings("PMD")
    public boolean updateStudentEmployment(String studentId, String courseId) {
        HttpRequest request = HttpRequest.newBuilder().PUT(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(studentService + "/apply?netId=" + studentId
                        + "&courseId=" + courseId)).build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new FailedUpdateStudentEmploymentException(studentId);
        }

        return true;
    }
}
