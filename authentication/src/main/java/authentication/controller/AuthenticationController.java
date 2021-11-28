package authentication.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import authentication.entities.Authentication;
import authentication.service.AuthenticationService;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("Authentication")
@RequestMapping("auth")
public class AuthenticationController {
    private final transient AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Returns all managements stored in the database.
     *
     * @return the list
     */
    @GetMapping("findAll")
    public List<Authentication> findAll() {
        return authenticationService.findAll();
    }


}
