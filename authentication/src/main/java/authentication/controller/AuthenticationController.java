package authentication.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import authentication.entities.Authentication;
import authentication.service.AuthenticationService;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/getAll")
    public List<Authentication> findAll() {
        return authenticationService.findAll();
    }

    @GetMapping("/getUserByNetID/{netId}")
    public Authentication getUserNetID(@PathVariable String netId){
        return authenticationService.getUserByNetId(netId);
    }


}
