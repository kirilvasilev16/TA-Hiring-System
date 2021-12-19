package management.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import management.entities.Management;
import management.repositories.ManagementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;

class DataConfigTest {

    private transient DataConfig dataConfig;
    private transient ManagementRepository managementRepository;
    private transient List<Management> list;

    @BeforeEach
    void setup() {
        dataConfig = new DataConfig();
        managementRepository = Mockito.mock(ManagementRepository.class);

        list = new ArrayList<>();

        String sem = "CSE2115-2022";
        String oopp = "CSE1105-2022";
        String co = "CSE1400-2021";

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
    }

    @Test
    void constructor() {
        assertNotNull(dataConfig);
    }

    @Test
    void setCommandLineRunner() throws Exception {
        CommandLineRunner commandLineRunner
                = dataConfig.commandLineRunner(managementRepository);

        commandLineRunner.run();
        Mockito.verify(managementRepository).saveAll(list);
    }
}