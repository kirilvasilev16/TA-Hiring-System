package authentication.controller;

import authentication.entities.Authentication;
import authentication.service.AuthenticationService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("Authentication")
@RequestMapping("authentication")
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
    @GetMapping("getAll")
    public List<Authentication> findAll() {
        return authenticationService.findAll();
    }

}
