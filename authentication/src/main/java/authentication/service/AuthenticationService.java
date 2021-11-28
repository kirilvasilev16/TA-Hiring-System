package authentication.service;

import authentication.entities.Authentication;
import authentication.repository.AuthenticationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    private final AuthenticationRepository authenticationRepository;

    public AuthenticationService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    /**
     * Finds all managements stored in the database.
     *
     * @return the list
     */
    public List<Authentication> findAll() {
        return authenticationRepository.findAll();
    }
}
