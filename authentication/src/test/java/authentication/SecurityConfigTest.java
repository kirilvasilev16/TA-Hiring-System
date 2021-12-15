package authentication;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import authentication.communication.ServerCommunication;
import authentication.controller.AuthenticationController;
import authentication.entities.Authentication;
import authentication.entities.Role;
import authentication.service.AuthenticationService;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest
public class  SecurityConfigTest {
    private transient MockMvc mvc;

    @MockBean
    private transient ServerCommunication serverCommunication;
    @Autowired
    private transient AuthenticationController authenticationController;

    @MockBean
    private transient AuthenticationService authenticationService;

    @MockBean
    private transient BCryptPasswordEncoder bcpe;

    private transient Authentication authTa;
    private transient Authentication authLecturer;
    private transient String findAllResult;
    private transient String courseId = "CSE1200";
    private transient String studentId = "kvasilev";

    @MockBean
    private transient MockHttpServletRequest request;
    @Autowired
    private transient WebApplicationContext context;

    @BeforeEach
    void setUp() {
        authTa = new Authentication("net2@id.nl", "pass2", "name",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_ta"))));
        authLecturer = new Authentication("net2@id.nl", "pass2", "name",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_lecturer"))));

        findAllResult = "{\"id\":1,\"courseId\":CSE1200,\"studentId\":kvasilev,\"amountOfHours\""
                + ":120.0,\"approvedHours\":50.0,\"declaredHours\":20.0,\"rating\":10.0}";

        this.mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

    }

    @Test
    void getMicroserviceNoAuthorization() throws Exception {
        mvc.perform(get("/management/findAll"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "student")
    void getMicroserviceWithAuthenticationStudent() throws Exception {
        mvc.perform(get("/management/findAll"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "admin")
    void getStudentMsWithAdminRole() throws Exception {
        mvc.perform(post("/student/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "student")
    void getStudentMsWithAdminRoleForbidden() throws Exception {
        mvc.perform(post("/student/add"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "student")
    void getStudentMsStudentCredential() throws Exception {
        mvc.perform(put("/student/apply"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "lecturer")
    void getStudentMsStudentCredentialForbidden() throws Exception {
        mvc.perform(put("/student/apply"))
                .andExpect(status().isForbidden());
    }
}
