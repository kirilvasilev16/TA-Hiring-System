package authentication.config;

import authentication.entities.Authentication;
import authentication.repository.AuthenticationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            Authentication auth = new Authentication("net@id.nl", "netid");
            managementRepository.save(auth);
        };
    }
}
