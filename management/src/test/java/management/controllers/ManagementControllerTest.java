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
import management.entities.Hours;
import management.entities.Management;
import management.services.ManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    private transient String courseIdString = "courseId";
    private transient String studentIdString = "studentId";

    @BeforeEach
    void setUp() {
        management1 = new Management(courseId, studentId, 120);
        management1.setId(1);
        management1.setRating(10.0f);
        management1.setDeclaredHours(20);
        management1.setApprovedHours(50);
        management2 = new Management(courseId, "aatanasov", 70);
        management2.setId(2);
        managements = new ArrayList<>();
        managements.add(management1);
        managements.add(management2);

        findAllResult = "[{\"id\":1,\"courseId\":CSE1200,\"studentId\":kvasilev,\"amountOfHours\""
                + ":120.0,\"approvedHours\":50.0,\"declaredHours\":20.0,\"rating\":10.0},"
                + "{\"id\":2,\"courseId\":CSE1200,\"studentId\":aatanasov,\"amountOfHours\":70.0,"
                + "\"approvedHours\":0.0,\"declaredHours\":0.0,\"rating\":-1.0}]";

        findOneResult = "{\"id\":1,\"courseId\":CSE1200,\"studentId\":kvasilev,\"amountOfHours\""
                + ":120.0,\"approvedHours\":50.0,\"declaredHours\":20.0,\"rating\":10.0}";

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.managementController)
                .setMessageConverters(new GsonHttpMessageConverter())
                .build();
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

        this.mockMvc.perform(get("/management/get?"
                + courseIdString + "=" + courseId
                + "&" + studentIdString + "=" + studentId))
                .andExpect(status().isOk())
                .andExpect(content().json(findOneResult));
    }

    @Test
    void getAverageRating() throws Exception {
        when(managementService.getAverageRating(studentId)).thenReturn(management1.getRating());

        this.mockMvc.perform(get("/management/getAverageRating?"
                + "&" + studentIdString + "=" + studentId))
                .andExpect(status().isOk())
                .andExpect(content().string("10.0"));
    }

    @Test
    void getRating() throws Exception {
        when(managementService.getOne(courseId, studentId)).thenReturn(management1);

        this.mockMvc.perform(get("/management/getRating?"
                + courseIdString + "=" + courseId
                + "&" + studentIdString + "=" + studentId))
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(management1.getRating())));
    }

    @Test
    void getAmountOfHours() throws Exception {
        when(managementService.getOne(courseId, studentId)).thenReturn(management1);

        this.mockMvc.perform(get("/management/getAmountOfHours?"
                + courseIdString + "=" + courseId
                + "&" + studentIdString + "=" + studentId))
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(management1.getAmountOfHours())));
    }

    @Test
    void getDeclaredHours() throws Exception {
        when(managementService.getOne(courseId, studentId)).thenReturn(management1);

        this.mockMvc.perform(get("/management/getDeclaredHours?"
                + courseIdString + "=" + courseId
                + "&" + studentIdString + "=" + studentId))
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(management1.getDeclaredHours())));
    }

    @Test
    void getApprovedHours() throws Exception {
        when(managementService.getOne(courseId, studentId)).thenReturn(management1);

        this.mockMvc.perform(get("/management/getApprovedHours?"
                + courseIdString + "=" + courseId
                + "&" + studentIdString + "=" + studentId))
                .andExpect(status().isOk())
                .andExpect(content().json(String.valueOf(management1.getApprovedHours())));
    }

    @Test
    void create() throws Exception {
        when(managementService.createManagement(courseId, studentId, 120))
                .thenReturn(management1);

        this.mockMvc
                .perform(post("/management/create?"
                        + courseIdString + "=" + courseId
                        + "&" + studentIdString + "=" + studentId
                        + "&amountOfHours=120"))
                .andExpect(status().isOk())
                .andExpect(content().json(findOneResult));
    }

    @Test
    void declareHours() throws Exception {
        this.mockMvc
                .perform(put("/management/declareHours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{" + courseIdString + ":" + courseId
                                + ",\"studentId\":" + studentId + ",\"hours\":20.0}]")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(managementService, only())
                .declareHours(List.of(new Hours(courseId, studentId, 20)));
    }

    @Test
    void declareHoursMultiple() throws Exception {
        this.mockMvc
                .perform(put("/management/declareHours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{" + courseIdString + ":" + courseId
                                + "," + studentIdString + ":" + studentId + ",\"hours\":10.0},"
                                + "{\"courseId\":" + "\"CSE1200\""
                                + ",\"studentId\":" + "\"kvasilev\"" + ",\"hours\":30.0}]")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(managementService, only())
                .declareHours(List.of(new Hours(courseId, studentId, 10),
                    new Hours("CSE1200", "kvasilev", 30)));
    }

    @Test
    void approveHours() throws Exception {
        this.mockMvc
                .perform(put("/management/approveHours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{" + courseIdString + ":" + courseId
                                + "," + studentIdString + ":" + studentId + ",\"hours\":20.0}]")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(managementService, only())
                .approveHours(List.of(new Hours(courseId, studentId, 20)));
    }

    @Test
    void approveHoursMultiple() throws Exception {
        this.mockMvc
                .perform(put("/management/approveHours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{" + courseIdString + ":" + courseId
                                + "," + studentIdString + ":" + studentId + ",\"hours\":10.0},"
                                + "{" + courseIdString + ":" + courseId
                                + "," + studentIdString + ":" + "\"kvasilev\""
                                + ",\"hours\":30.0}]")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(managementService, only())
                .approveHours(List.of(new Hours(courseId, studentId, 10),
                    new Hours("CSE1200", "kvasilev", 30)));
    }

    @Test
    void rate() throws Exception {
        this.mockMvc
                .perform(put("/management/rate?"
                        + courseIdString + "=" + courseId
                        + "&" + studentIdString + "=" + studentId
                        + "&rating=2.5"))
                .andExpect(status().isOk());
        verify(managementService, only())
                .rateStudent(courseId, studentId, 2.5f);
    }

    @Test
    void sendContract() throws Exception {
        this.mockMvc
                .perform(get("/management/sendContract?"
                        + courseIdString + "=" + courseId
                        + "&" + studentIdString + "=" + studentId
                        + "&email=email@gmail.com"))
                .andExpect(status().isOk());
        verify(managementService, only())
                .sendContract(courseId, studentId, "email@gmail.com");
    }
}