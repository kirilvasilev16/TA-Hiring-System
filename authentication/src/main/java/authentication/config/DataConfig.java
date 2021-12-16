package authentication.config;

import authentication.entities.Authentication;
import authentication.entities.Role;
import authentication.service.AuthenticationService;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataConfig {

    /**
     * Set up the mocked data.
     *
     * @param authenticationService auth service
     * @return predefined arguments
     */
    @Bean
    CommandLineRunner commandLineRunner(AuthenticationService authenticationService) {
        return args -> {

            authenticationService.saveRole(new Role("ROLE_student"));
            authenticationService.saveRole(new Role("ROLE_ta"));
            authenticationService.saveRole(new Role("ROLE_lecturer"));
            authenticationService.saveRole(new Role("ROLE_admin"));

            authenticationService.saveAuth(new Authentication("net1@id.nl", "email@email.co",
                    bcPasswordEncoder().encode("pass1"),
                    "netid1",  new ArrayList<Role>()));
            authenticationService.saveAuth(new Authentication("net2@id.nl", "email@email.co",
                    bcPasswordEncoder().encode("pass2"),
                    "netid2",  new ArrayList<Role>()));
            authenticationService.saveAuth(new Authentication("net3@id.nl", "email@email.co",
                    bcPasswordEncoder().encode("pass3"),
                    "netid3",  new ArrayList<Role>()));
            authenticationService.saveAuth(new Authentication("net4@id.nl", "email@email.co",
                    bcPasswordEncoder().encode("pass4"),
                    "netid4",  new ArrayList<Role>()));

            authenticationService.addRoleToAuthentication("net1@id.nl", "ROLE_student");
            authenticationService.addRoleToAuthentication("net2@id.nl", "ROLE_ta");
            authenticationService.addRoleToAuthentication("net3@id.nl", "ROLE_lecturer");
            authenticationService.addRoleToAuthentication("net4@id.nl", "ROLE_admin");
        };
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
