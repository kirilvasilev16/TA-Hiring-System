package student.communication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CourseCommunicationTest {

    private transient CourseCommunication courseCommunication;
    private transient Set<String> set;
    private transient HttpClient client;
    private transient HttpResponse<String> response;
    private transient Gson gson;
    private final transient String netId = "ohageman";
    private final transient String courseId = "CSE2115-2022";

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        courseCommunication = new CourseCommunication();
        set = new HashSet<>();
        client = Mockito.mock(HttpClient.class);
        courseCommunication.setClient(client);
        response = Mockito.mock(HttpResponse.class);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);
        gson = new GsonBuilder().create();
    }

    @Test
    void checkApplyRequirementTrueTest() {
        Mockito.when(response.statusCode()).thenReturn(200);
        assertDoesNotThrow(() ->
                courseCommunication.checkApplyRequirement(netId, courseId, set));
        assertTrue(courseCommunication.checkApplyRequirement(netId, courseId, set));
    }

    @Test
    void checkApplyRequirementFalseTest() {
        Mockito.when(response.statusCode()).thenReturn(500);
        assertDoesNotThrow(() ->
                courseCommunication.checkApplyRequirement(netId,
                        courseId, set));
        assertFalse(courseCommunication.checkApplyRequirement(netId, courseId, set));
    }

    @Test
    void removeAsCandidateTrueTest() {
        Mockito.when(response.statusCode()).thenReturn(200);
        assertDoesNotThrow(() ->
                courseCommunication.removeAsCandidate(netId, courseId));
        assertTrue(courseCommunication.removeAsCandidate(netId, courseId));
    }

    @Test
    void removeAsCandidateFalseTest() {
        Mockito.when(response.statusCode()).thenReturn(500);
        assertDoesNotThrow(() ->
                courseCommunication.removeAsCandidate(netId, courseId));
        assertFalse(courseCommunication.removeAsCandidate(netId, courseId));
    }

    @Test
    void averageWorkedHoursTest() {
        Mockito.when(response.body()).thenReturn(gson.toJson(20.0));
        assertDoesNotThrow(() ->
                courseCommunication.averageWorkedHours(courseId));
        assertEquals(20.0, courseCommunication.averageWorkedHours(courseId));
    }

}
