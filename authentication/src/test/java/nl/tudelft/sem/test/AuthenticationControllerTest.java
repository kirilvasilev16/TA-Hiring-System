package nl.tudelft.sem.test;

import authentication.controller.AuthenticationController;
import authentication.entities.Authentication;
import authentication.entities.Role;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@WebMvcTest
public class AuthenticationControllerTest {

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient AuthenticationController authenticationController;

    private transient Authentication authTa;
    private transient Authentication authLecturer;
    private transient String findAllResult;
    private transient String courseId = "CSE1200";
    private transient String studentId = "kvasilev";

    @BeforeEach
    void setUp() {
        authTa = new Authentication("net2@id.nl", "pass2", "name",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_ta"))));
        authLecturer = new Authentication("net2@id.nl", "pass2", "name",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_lecturer"))));

        findAllResult = "{\"id\":1,\"courseId\":CSE1200,\"studentId\":kvasilev,\"amountOfHours\""
                + ":120.0,\"approvedHours\":0.0,\"declaredHours\":0.0,\"rating\":0.0}";
    }

    /*
    @Test
    void getManagement() throws Exception {
        when(ServerCommunication.getRequest("http://localhost:8081/management/findAll"))
        .thenReturn(findAllResult);

        this.mockMvc.perform(get("/management/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(findAllResult));
    }
*/
}
