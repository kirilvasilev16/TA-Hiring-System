package authentication.controller;

import authentication.communication.ServerCommunication;
import authentication.service.AuthenticationService;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("Authentication")
@RequestMapping("/")
public class AuthenticationController {
    private final transient AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * get request handler, sends get request to api-gateway.
     *
     * @param request sent by user
     * @return response string
     * @throws IOException exception
     */
    @GetMapping("/**")
    public String get(HttpServletRequest request) throws IOException {
        return ServerCommunication.getRequest(request.getRequestURI()
                + "?" + request.getQueryString());
    }

    /**
     * put request handler, sends put request to api-gateway.
     *
     * @param request sent by user
     * @return response string
     * @throws IOException exception
     */
    @PutMapping("/**")
    public String put(HttpServletRequest request) throws IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        return ServerCommunication.putRequest(request.getRequestURI()
                + "?" + request.getQueryString(), body);
    }

    /**
     * post request handler, sends post request to api-gateway.
     *
     * @param request sent by user
     * @return response string
     * @throws IOException exception
     */
    @PostMapping("/**")
    public String post(HttpServletRequest request) throws IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        return ServerCommunication.postRequest(request.getRequestURI()
                + "?" + request.getQueryString(), body);
    }

}
