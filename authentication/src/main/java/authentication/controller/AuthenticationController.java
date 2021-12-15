package authentication.controller;

import authentication.communication.ServerCommunication;
import authentication.service.AuthenticationService;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("AvoidFieldNameMatchingMethodName")
@RestController("Authentication")
@RequestMapping("/")
public class AuthenticationController {
    @Autowired
    private transient AuthenticationService authenticationService;
    @Autowired
    private transient ServerCommunication serverCommunication;

    public AuthenticationController() {}

    /**
     * get request handler, sends get request to api-gateway.
     *
     * @param request sent by user
     * @return response string
     * @throws IOException exception
     */
    @GetMapping("/**")
    public String get(HttpServletRequest request) throws IOException {
        if (request.getQueryString() == null || request.getQueryString().length() == 0) {
            return serverCommunication.getRequest(request.getRequestURI());
        } else {
            return serverCommunication.getRequest(request.getRequestURI()
                    + "?" + request.getQueryString());
        }

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
        String body = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
        if (request.getQueryString() == null || request.getQueryString().length() == 0) {
            return serverCommunication.putRequest(request.getRequestURI(), body);
        } else {
            return serverCommunication.putRequest(request.getRequestURI()
                    + "?" + request.getQueryString(), body);
        }

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
        String body = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
        if (request.getQueryString() == null || request.getQueryString().length() == 0) {
            return serverCommunication.postRequest(request.getRequestURI(), body);
        } else {
            return serverCommunication.postRequest(request.getRequestURI()
                    + "?" + request.getQueryString(), body);
        }
    }

    @Bean
    public ServerCommunication serverCommunicationBean() {
        return new ServerCommunication();
    }
}
