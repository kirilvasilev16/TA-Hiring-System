package lecturer.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import lecturer.entities.Lecturer;
import lecturer.repositories.LecturerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.CommandLineRunner;

public class DataConfigTest {
    private transient DataConfig dataConfig;
    private transient LecturerRepository lecturerRepository;
    private transient Lecturer lecturer1;
    private transient Lecturer lecturer2;
    private transient Lecturer lecturer3;
    private transient Lecturer lecturer4;
    private transient Lecturer lecturer5;

    @BeforeEach
    void setUp() {
        dataConfig = new DataConfig();
        lecturerRepository = Mockito.mock(LecturerRepository.class);
        List<String> l1 = new ArrayList<>();
        l1.add("CSE1400-2022");
        lecturer1 = new Lecturer("taerts", "Taico", "taerts@tudelft.nl", l1);
        List<String> l2 = new ArrayList<>();
        l2.add("CSE1105-2022");
        l2.add("CSE2115-2022");
        lecturer2 = new Lecturer("gmigut", "Gosia", "gmigut@tudelft.nl", l2);
        List<String> l3 = new ArrayList<>();
        l3.add("CSE2115-2022");
        lecturer3 = new Lecturer("azaidman", "Andy", "azaidman@tudelft.nl", l3);
        lecturer4 = new Lecturer("toverklift", "Tomas", "toverklift@tudelft.nl", l3);
        lecturer5 = new Lecturer("mrhug", "Stefan", "mrhug@tudelft.nl", l2);
    }

    @Test
    void constructor() {
        assertNotNull(dataConfig);
    }

    @Test
    void setCommandLineRunner() throws Exception {
        CommandLineRunner commandLineRunner
                = dataConfig.commandLineRunner(lecturerRepository);

        commandLineRunner.run();
        Mockito.verify(lecturerRepository).save(lecturer1);
        Mockito.verify(lecturerRepository).save(lecturer2);
        Mockito.verify(lecturerRepository).save(lecturer3);
        Mockito.verify(lecturerRepository).save(lecturer4);
        Mockito.verify(lecturerRepository).save(lecturer5);

    }
}
