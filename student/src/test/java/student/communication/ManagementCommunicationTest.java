package student.communication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ManagementCommunicationTest {

    private transient ManagementCommunication managementCommunication;
    private transient String json;
    private transient HttpClient client;
    private transient HttpResponse<String> response;


    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        managementCommunication = new ManagementCommunication();
        json = "[{\n"
                + "    \"courseId\" : \"CSE2115-2022\",\n"
                + "    \"studentId\" : \"kvasilev\",\n"
                + "    \"hours\" : 20.0\n"
                + "}]";
        client = Mockito.mock(HttpClient.class);
        managementCommunication.setClient(client);
        response = Mockito.mock(HttpResponse.class);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);
    }

    @Test
    void declareHoursTrueTest() {
        Mockito.when(response.statusCode()).thenReturn(200);
        assertDoesNotThrow(() ->
                managementCommunication.declareHours(json));
        assertTrue(managementCommunication.declareHours(json));
    }

    @Test
    void declareHoursFalseTest() {
        Mockito.when(response.statusCode()).thenReturn(500);
        assertDoesNotThrow(() ->
                managementCommunication.declareHours(json));
        assertFalse(managementCommunication.declareHours(json));
    }
}
