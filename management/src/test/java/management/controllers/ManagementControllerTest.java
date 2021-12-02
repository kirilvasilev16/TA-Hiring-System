package management.controllers;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import management.entities.Management;
import management.services.ManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@WebMvcTest
class ManagementControllerTest {

    @Autowired
    private transient MockMvc mockMvc;
    @Autowired
    private transient ManagementController managementController;
    @MockBean
    private transient ManagementService managementService;

    private transient Management management1;
    private transient Management management2;
    private transient List<Management> managements;
    private transient String findAllResult;
    private transient String findOneResult;
    private transient String courseId = "CSE1200";
    private transient String studentId = "kvasilev";

    @BeforeEach
    void setUp() {
        management1 = new Management("CSE1200", "kvasilev", 120);
        management1.setId(1);
        management2 = new Management("CSE1200", "aatanasov", 70);
        management2.setId(2);
        managements = new ArrayList<>();
        managements.add(management1);
        managements.add(management2);

        findAllResult = "[{\"id\":1,\"courseId\":CSE1200,\"studentId\":kvasilev,\"amountOfHours\""
                + ":120.0,\"approvedHours\":0.0,\"declaredHours\":0.0,\"rating\":0.0},"
                + "{\"id\":2,\"courseId\":CSE1200,\"studentId\":aatanasov,\"amountOfHours\":70.0,"
                + "\"approvedHours\":0.0,\"declaredHours\":0.0,\"rating\":0.0}]";

        findOneResult = "{\"id\":1,\"courseId\":CSE1200,\"studentId\":kvasilev,\"amountOfHours\""
                + ":120.0,\"approvedHours\":0.0,\"declaredHours\":0.0,\"rating\":0.0}";
    }

    @Test
    void findAll() throws Exception {
        when(managementService.findAll()).thenReturn(managements);

        this.mockMvc.perform(get("/management/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(findAllResult));
    }

    @Test
    void getOne() throws Exception {
        when(managementService.getOne(courseId, studentId)).thenReturn(management1);

        this.mockMvc.perform(get("/management/get?courseId=" + courseId
        + "&studentId=" + studentId))
                .andExpect(status().isOk())
                .andExpect(content().json(findOneResult));
    }

    @Test
    void create() throws Exception {
        when(managementService.createManagement(courseId, studentId, 120))
                .thenReturn(management1);

        this.mockMvc
                .perform(post("/management/create?courseId=CSE1200&"
                        + "studentId=kvasilev&amountOfHours=120"))
                .andExpect(status().isOk())
                .andExpect(content().json(findOneResult));
    }

    @Test
    void declareHours() throws Exception {
        this.mockMvc
                .perform(put("/management/declareHours?courseId=" + courseId
                        + "&studentId=" + studentId + "&hours=10"))
                .andExpect(status().isOk());
        verify(managementService, only()).declareHours(courseId, studentId, 10);
    }

    @Test
    void approveHours() throws Exception {
        this.mockMvc
                .perform(put("/management/approveHours?courseId=" + courseId
                        + "&studentId=" + studentId + "&hours=20"))
                .andExpect(status().isOk());
        verify(managementService, only()).approveHours(courseId, studentId, 20);
    }

    @Test
    void rate() throws Exception {
        this.mockMvc
                .perform(put("/management/rate?courseId=" + courseId
                        + "&studentId=" + studentId + "&rating=2.5"))
                .andExpect(status().isOk());
        verify(managementService, only()).rateStudent(courseId, studentId, 2.5f);
    }

    @Test
    void sendContract() throws Exception {
        this.mockMvc
                .perform(get("/management/sendContract?courseId=" + courseId
                        + "&studentId=" + studentId + "&email=email@gmail.com"))
                .andExpect(status().isOk());
        verify(managementService, only()).sendContract(courseId, studentId, "email@gmail.com");
    }
}