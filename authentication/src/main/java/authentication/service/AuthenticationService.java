package authentication.service;

import authentication.entities.Authentication;
import authentication.entities.Role;
import authentication.repository.AuthenticationRepository;
import authentication.repository.RoleRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {

    private final transient AuthenticationRepository authenticationRepository;
    private final transient RoleRepository roleRepository;

    public AuthenticationService(AuthenticationRepository authenticationRepository,
                                 RoleRepository roleRepository) {
        this.authenticationRepository = authenticationRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Finds all authentication objects stored in the database.
     *
     * @return the list
     */
    public List<Authentication> findAll() {
        return authenticationRepository.findAll();
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public Authentication saveAuth(Authentication authentication) {
        authentication.setPassword(bcPasswordEncoder().encode(authentication.getPassword()));
        return authenticationRepository.save(authentication);
    }

    /**
     * add a role to the user authentication object.
     *
     * @param netId of the user
     * @param roleName role of the user
     * @return return authentication object
     */
    public String addRoleToAuthentication(String netId, String roleName) {
        Authentication auth = authenticationRepository.findByNetId(netId);
        Role role = roleRepository.findByName(roleName);
        auth.getRoles().add(role);
        authenticationRepository.save(auth);
        return "Role added successfully";
    }

    /**
     * loads user by username, in this case netId, is used by UserDetailsService.
     *
     * @param netId of user
     * @return UserDetails object
     * @throws UsernameNotFoundException if username is not found
     */
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

    public List<Authentication> saveAll(List<Authentication> authList) {
        return authenticationRepository.saveAll(authList);
    }

    /**
     * BCryptPasswordEncoder creating function.
     *
     * @return a new BCryptPasswordEncoder
     */
    @Bean
    BCryptPasswordEncoder bcPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
