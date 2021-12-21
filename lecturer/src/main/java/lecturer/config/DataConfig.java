package lecturer.config;

import java.util.ArrayList;
import java.util.List;
import lecturer.entities.Lecturer;
import lecturer.repositories.LecturerRepository;
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
    CommandLineRunner commandLineRunner(LecturerRepository lecturerRepo) {
        return args -> {

            List<String> l1 = new ArrayList<>();
            l1.add("CSE1400-2021");
            Lecturer lecturer1 = new Lecturer("taerts", "Taico", "taerts@tudelft.nl", l1);
            lecturerRepo.save(lecturer1);
            List<String> l2 = new ArrayList<>();
            l2.add("CSE1105-2022");
            l2.add("CSE2115-2022");
            Lecturer lecturer2 = new Lecturer("gmigut", "Gosia", "gmigut@tudelft.nl", l2);
            List<String> l3 = new ArrayList<>();
            l3.add("CSE2115-2022");
            Lecturer lecturer3 = new Lecturer("azaidman", "Andy", "azaidman@tudelft.nl", l3);
            Lecturer lecturer4 = new Lecturer("toverklift", "Tomas", "toverklift@tudelft.nl", l3);
            Lecturer lecturer5 = new Lecturer("mrhug", "Stefan", "mrhug@tudelft.nl", l2);
            Lecturer lecturer6 = new Lecturer("ovisser", "Otto", "ovisser@tudelft.nl", l1);
            lecturerRepo.save(lecturer2);
            lecturerRepo.save(lecturer3);
            lecturerRepo.save(lecturer4);
            lecturerRepo.save(lecturer5);
            lecturerRepo.save(lecturer6);
        };
    }
}
