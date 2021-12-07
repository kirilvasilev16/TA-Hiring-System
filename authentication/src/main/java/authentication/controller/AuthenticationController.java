package authentication.controller;

import authentication.communication.ServerCommunication;
import authentication.entities.Authentication;
import authentication.entities.Management;
import authentication.service.AuthenticationService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController("Authentication")
@RequestMapping("/")
public class AuthenticationController {
    private final transient AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Returns all authentications stored in the database.
     *
     * @return the list
     */
    @GetMapping("/**")
    public String get(HttpServletRequest request) throws IOException {
        System.out.println("request received");
        return ServerCommunication.getRequest(request.getRequestURI() + "?" + request.getQueryString());
    }
    @PutMapping("/**")
    public String put(HttpServletRequest request) throws IOException {
        return ServerCommunication.putRequest(request.getRequestURI() + "?" + request.getQueryString());
    }
    @PostMapping("/**")
    public String post(HttpServletRequest request) throws IOException {
        return ServerCommunication.postRequest(request.getRequestURI() + "?" + request.getQueryString());
    }

}
