package authentication.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class RequestTest {

    private transient Request req;
    @Mock
    private transient HttpClient httpClient;
    private transient HttpResponse<String> response;
    private transient SecurityContext securityContext;
    private transient Authentication auth;
    private transient SecurityContextHolder securityContextHolder;
    private transient HttpClient client;

    @BeforeEach
    void setUp() {
        client = Mockito.mock(HttpClient.class);
        securityContext = Mockito.mock(SecurityContext.class);
        securityContextHolder = Mockito.mock(SecurityContextHolder.class);
        response = Mockito.mock(HttpResponse.class);
        auth = Mockito.mock(Authentication.class);
        req = new Request(securityContextHolder);
    }

    @Test
    void getTest() throws IOException, InterruptedException {

        Mockito.doNothing().when(securityContextHolder).setSecurityContext();
        Mockito.when(securityContextHolder.getSecurityContext()).thenReturn(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn("netId");
        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.when(response.body()).thenReturn("body");
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Request.setClient(client);
        securityContextHolder.setSecurityContext();

        assertEquals(200, req
                .get("http://localhost:8080").getStatusCode());
        assertEquals("body", req
                .get("http://localhost:8080").getResult());
    }

    @Test
    void getTestError() throws IOException, InterruptedException {

        Mockito.doNothing().when(securityContextHolder).setSecurityContext();
        Mockito.when(securityContextHolder.getSecurityContext()).thenReturn(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn("netId");

        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenThrow(new IOException());

        Request.setClient(client);
        securityContextHolder.setSecurityContext();

        assertEquals(500, req
                .get("http://localhost:8080").getStatusCode());
        assertEquals("Unexpected error, please try again!", req
                .get("http://localhost:8080").getResult());
    }

    @Test
    void putTest() throws IOException, InterruptedException {

        Mockito.doNothing().when(securityContextHolder).setSecurityContext();
        Mockito.when(securityContextHolder.getSecurityContext()).thenReturn(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn("netId");
        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.when(response.body()).thenReturn("body");
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Request.setClient(client);
        securityContextHolder.setSecurityContext();

        assertEquals(200, req
                .put("http://localhost:8080", "").getStatusCode());
        assertEquals("body", req
                .put("http://localhost:8080", "").getResult());
    }

    @Test
    void putTestError() throws IOException, InterruptedException {

        Mockito.doNothing().when(securityContextHolder).setSecurityContext();
        Mockito.when(securityContextHolder.getSecurityContext()).thenReturn(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn("netId");
        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.when(response.body()).thenReturn("body");
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenThrow(new IOException());

        Request.setClient(client);
        securityContextHolder.setSecurityContext();

        assertEquals(500, req
                .put("http://localhost:8080", "").getStatusCode());
        assertEquals("Unexpected error, please try again!", req
                .put("http://localhost:8080", "").getResult());
    }

    @Test
    void putTestBody() throws IOException, InterruptedException {

        Mockito.doNothing().when(securityContextHolder).setSecurityContext();
        Mockito.when(securityContextHolder.getSecurityContext()).thenReturn(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn("netId");
        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.when(response.body()).thenReturn("body");
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Request.setClient(client);
        securityContextHolder.setSecurityContext();

        assertEquals(200, req
                .put("http://localhost:8080", "{body:body}").getStatusCode());
        assertEquals("body", req.put("http://localhost:8080", "{body:body}").getResult());

    }

    @Test
    void putTestBodyNull() throws IOException, InterruptedException {

        Mockito.doNothing().when(securityContextHolder).setSecurityContext();
        Mockito.when(securityContextHolder.getSecurityContext()).thenReturn(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn("netId");
        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.when(response.body()).thenReturn("body");
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Request.setClient(client);
        securityContextHolder.setSecurityContext();

        assertEquals(200, req
                .put("http://localhost:8080", null).getStatusCode());
        assertEquals("body", req.put("http://localhost:8080", null).getResult());

    }

    @Test
    void postTest() throws IOException, InterruptedException {

        Mockito.doNothing().when(securityContextHolder).setSecurityContext();
        Mockito.when(securityContextHolder.getSecurityContext()).thenReturn(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn("netId");
        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.when(response.body()).thenReturn("body");
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Request.setClient(client);
        securityContextHolder.setSecurityContext();

        assertEquals(200, req
                .post("http://localhost:8080", "").getStatusCode());
        assertEquals("body", req
                .post("http://localhost:8080", "").getResult());
    }

    @Test
    void postTestError() throws IOException, InterruptedException {

        Mockito.doNothing().when(securityContextHolder).setSecurityContext();
        Mockito.when(securityContextHolder.getSecurityContext()).thenReturn(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn("netId");
        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.when(response.body()).thenReturn("body");
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenThrow(new IOException());

        Request.setClient(client);
        securityContextHolder.setSecurityContext();

        assertEquals(500, req
                .post("http://localhost:8080", "").getStatusCode());
        assertEquals("Unexpected error, please try again!", req
                .post("http://localhost:8080", "").getResult());
    }

    @Test
    void postTestBody() throws IOException, InterruptedException {

        Mockito.doNothing().when(securityContextHolder).setSecurityContext();
        Mockito.when(securityContextHolder.getSecurityContext()).thenReturn(securityContext);
        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn("netId");
        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.when(response.body()).thenReturn("body");
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Request.setClient(client);
        securityContextHolder.setSecurityContext();

        assertEquals(200, req
                .post("http://localhost:8080", "{body:body}").getStatusCode());
        assertEquals("body", req
                .post("http://localhost:8080", "{body:body}").getResult());

    }


    @Test
    void postTestBodyNull() throws IOException, InterruptedException {

        Mockito.doNothing().when(securityContextHolder).setSecurityContext();
        Mockito.when(securityContextHolder.getSecurityContext()).thenReturn(securityContext);

        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.when(auth.getPrincipal()).thenReturn("netId");
        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.when(response.body()).thenReturn("body");
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Request.setClient(client);
        securityContextHolder.setSecurityContext();

        assertEquals(200, req
                .post("http://localhost:8080", null).getStatusCode());
        assertEquals("body", req
                .post("http://localhost:8080", null).getResult());

    }

    @Test
    void getClientTest() {
        Request.setClient(httpClient);
        assertEquals(httpClient, Request.getClient());
    }
}
