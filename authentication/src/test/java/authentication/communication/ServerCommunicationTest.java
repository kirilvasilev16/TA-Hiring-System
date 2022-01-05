package authentication.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import authentication.controller.AuthenticationController;
import authentication.entities.ResponseObj;
import authentication.service.AuthenticationService;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ServerCommunicationTest {

    private transient ServerCommunication serverCommunication;

    @MockBean
    private transient Request request;

    @Autowired
    private transient WebApplicationContext context;

    @MockBean
    private transient AuthenticationService authenticationService;

    @MockBean
    private transient AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        request = Mockito.mock(Request.class);
        serverCommunication = new ServerCommunication(request);
    }

    @Test
    void getRequestTest() throws IOException {
        when(request.get("http://localhost:8761/management/findAll"))
                .thenReturn(new ResponseObj("result", 200));
        assertEquals("result", serverCommunication
                .getRequest("/management/findAll").getResult());
    }

    @Test
    void putRequestTest() throws IOException {
        when(request.put("http://localhost:8761/management/findAll", ""))
                .thenReturn(new ResponseObj("result", 200));
        assertEquals("result", serverCommunication
                .putRequest("/management/findAll", "").getResult());
    }

    @Test
    void postRequestTest() throws IOException {
        when(request.post("http://localhost:8761/management/findAll", ""))
                .thenReturn(new ResponseObj("result", 200));
        assertEquals("result", serverCommunication
                .postRequest("/management/findAll", "").getResult());
    }
}
