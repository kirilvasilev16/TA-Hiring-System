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

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
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

            authenticationService.saveAuth(new Authentication("kvasilev", "email@email.co",
                    "kvasilev123",
                    "netid1",  new ArrayList<Role>()));
            authenticationService.saveAuth(new Authentication("net2@id.nl", "email@email.co",
                    "pass2",
                    "netid2",  new ArrayList<Role>()));
            authenticationService.saveAuth(new Authentication("azaidman", "email@email.co",
                    "azaidman123",
                    "netid3",  new ArrayList<Role>()));
            authenticationService.saveAuth(new Authentication("mrhug", "email@email.co",
                    "mrhug123",
                    "kiril",  new ArrayList<Role>()));

            authenticationService.addRoleToAuthentication("kvasilev", "ROLE_student");
            authenticationService.addRoleToAuthentication("net2@id.nl", "ROLE_ta");
            authenticationService.addRoleToAuthentication("azaidman", "ROLE_lecturer");
            authenticationService.addRoleToAuthentication("mrhug", "ROLE_admin");
        };
    }
}
