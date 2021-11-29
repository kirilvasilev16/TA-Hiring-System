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
            Authentication auth1 = new Authentication("net1", "netid1");
            Authentication auth2 = new Authentication("net2@id.nl", "netid2");
            Authentication auth3 = new Authentication("net3@id.nl", "netid3");
            Authentication auth4 = new Authentication("net4@id.nl", "netid4");
            Authentication auth5 = new Authentication("net5@id.nl", "netid5");
            managementRepository.save(auth1);
            managementRepository.save(auth2);
            managementRepository.save(auth3);
            managementRepository.save(auth4);
            managementRepository.save(auth5);
        };
    }
}
