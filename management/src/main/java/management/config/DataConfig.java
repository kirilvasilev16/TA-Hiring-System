package management.config;

import java.util.ArrayList;
import java.util.List;
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

            List<Management> list = new ArrayList<>();

            String sem = "CSE2115-2022";
            String oopp = "CSE1105-2022";
            String co = "CSE1400-2022";

            String kvasilev = "kvasilev";
            String etrinh = "etrinh";
            String esozen = "esozen";
            String chengmouyeh = "chengmouyeh";
            String akalandadze = "akalandadze";
            String ohageman = "ohageman";

            Management semkvasilev = new Management(sem, kvasilev, 60);
            Management semetrinh = new Management(sem, etrinh, 120);
            list.add(semkvasilev);
            list.add(semetrinh);

            Management ooppesozen = new Management(oopp, esozen, 90);
            Management ooppchengmouyeh = new Management(oopp, chengmouyeh, 30);
            Management ooppakalandadze = new Management(oopp, akalandadze, 30);
            Management ooppohageman = new Management(oopp, ohageman, 600);
            list.add(ooppakalandadze);
            list.add(ooppesozen);
            list.add(ooppchengmouyeh);
            list.add(ooppohageman);

            Management coesozen = new Management(co, esozen, 20);
            Management cochengmouyeh = new Management(co, chengmouyeh, 120);
            list.add(coesozen);
            list.add(cochengmouyeh);

            managementRepository.saveAll(list);
        };
    }
}
