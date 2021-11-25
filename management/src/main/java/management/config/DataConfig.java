package management.config;

import management.entities.Management;
import management.repositories.ManagementRepository;
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
    CommandLineRunner commandLineRunner(ManagementRepository managementRepository) {
        return args -> {
            Management management = new Management("management");
            managementRepository.save(management);
        };
    }
}
