package authentication.service;

import authentication.entities.Authentication;
import authentication.repository.AuthenticationRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {

    private final transient AuthenticationRepository authenticationRepository;

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

    public Authentication getUserByNetId(String netId) {
        return authenticationRepository.findByNetId(netId);
    }

    @Override
    public UserDetails loadUserByUsername(String netId) throws UsernameNotFoundException {
        Authentication auth = authenticationRepository.findByNetId(netId);
        if (auth == null) {
            throw new UsernameNotFoundException("NetID not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        auth.getRoles()
                .forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role.getName()));
                });
        return new org.springframework.security
                .core.userdetails.User(auth.getNetId(), auth.getPassword(), authorities);
    }
}
