package authentication.config;

import authentication.entities.Authentication;
import authentication.repository.AuthenticationRepository;
import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataConfig {

    /**
     * Set up the mocked data.
     *
     * @param managementRepository the management repository
     * @return predefined arguments
     */
    @Bean
    CommandLineRunner commandLineRunner(AuthenticationRepository managementRepository) {
        return args -> {
            Authentication auth1 =
                    new Authentication("net1@id.nl", bcPasswordEncoder().encode("pass1"),
                            "netid1", new ArrayList());
            Authentication auth2 =
                    new Authentication("net2@id.nl", bcPasswordEncoder().encode("pass2"),
                            "netid2", new ArrayList());
            Authentication auth3 =
                    new Authentication("net3@id.nl", bcPasswordEncoder().encode("pass3"),
                            "netid3", new ArrayList());
            Authentication auth4 =
                    new Authentication("net4@id.nl", bcPasswordEncoder().encode("pass4"),
                            "netid4", new ArrayList());
            Authentication auth5 =
                    new Authentication("net5@id.nl", bcPasswordEncoder().encode("pass5"),
                            "netid5", new ArrayList());
            managementRepository.save(auth1);
            managementRepository.save(auth2);
            managementRepository.save(auth3);
            managementRepository.save(auth4);
            managementRepository.save(auth5);
        };
    }

    @Bean
    BCryptPasswordEncoder bcPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
