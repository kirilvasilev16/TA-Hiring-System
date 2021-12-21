package authentication.controller;

import authentication.communication.ServerCommunication;
import authentication.entities.Authentication;
import authentication.entities.ResponseObj;
import authentication.entities.Role;
import authentication.service.AuthenticationService;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * saves user to the local database.
     *
     * @param auth object representing the user.
     */
    @PostMapping("/authentication/saveAuth")
    public Authentication saveUser(@RequestBody Authentication auth) {
        return authenticationService.saveAuth(auth);
    }

    /**
     * saves role to the local database.
     *
     * @param role object representing the user.
     */
    @PostMapping("/authentication/saveRole")
    public Role saveRole(@RequestBody Role role) {
        return authenticationService.saveRole(role);
    }

    /**
     * add role to the auth object.
     *
     * @param netId of the auth object which represents the user
     * @param roleName name of the role
     */
    @PutMapping("/authentication/saveRole")
    public String addRoleToUser(@PathParam("netId") String netId,
                              @PathParam("roleName") String roleName) {
        return authenticationService.addRoleToAuthentication(netId, roleName);
    }

    /**
     * get request handler, sends get request to api-gateway.
     *
     * @param request sent by user
     * @return response string
     * @throws IOException exception
     */
    @GetMapping("/**")
    public ResponseEntity get(HttpServletRequest request) throws IOException {
        if (request.getQueryString() == null || request.getQueryString().length() == 0) {
            ResponseObj resp = serverCommunication.getRequest(request.getRequestURI());
            return new ResponseEntity(resp.getResult(), HttpStatus.valueOf(resp.getStatusCode()));
        } else {
            ResponseObj resp = serverCommunication
                    .getRequest(request.getRequestURI()
                            + "?" + request.getQueryString());
            return new ResponseEntity(resp.getResult(), HttpStatus.valueOf(resp.getStatusCode()));
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
    public ResponseEntity put(HttpServletRequest request) throws IOException {
        String body = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
        if (request.getQueryString() == null || request.getQueryString().length() == 0) {
            ResponseObj resp = serverCommunication
                    .putRequest(request.getRequestURI(), body);
            return new ResponseEntity(resp.getResult(), HttpStatus.valueOf(resp.getStatusCode()));
        } else {
            ResponseObj resp = serverCommunication.putRequest(request.getRequestURI()
                    + "?" + request.getQueryString(), body);
            return new ResponseEntity(resp.getResult(), HttpStatus.valueOf(resp.getStatusCode()));
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
    public ResponseEntity post(HttpServletRequest request) throws IOException {
        String body = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));
        if (request.getQueryString() == null || request.getQueryString().length() == 0) {
            ResponseObj resp = serverCommunication
                    .postRequest(request.getRequestURI(), body);
            return new ResponseEntity(resp.getResult(), HttpStatus.valueOf(resp.getStatusCode()));
        } else {
            ResponseObj resp = serverCommunication.postRequest(request.getRequestURI()
                    + "?" + request.getQueryString(), body);
            return new ResponseEntity(resp.getResult(), HttpStatus.valueOf(resp.getStatusCode()));
        }
    }

        @Bean
    public ServerCommunication serverCommunicationBean() {
        return new ServerCommunication();
    }
}
