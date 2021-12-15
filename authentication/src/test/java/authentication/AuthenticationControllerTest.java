package authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import authentication.communication.ServerCommunication;
import authentication.controller.AuthenticationController;
import authentication.entities.Authentication;
import authentication.entities.Role;
import authentication.service.AuthenticationService;
import java.util.ArrayList;
import java.util.Arrays;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@WebMvcTest
public class AuthenticationControllerTest {

    private transient MockMvc mvc;

    @MockBean
    private transient  ServerCommunication serverCommunication;
    @Autowired
    private transient AuthenticationController authenticationController;

    @MockBean
    private transient AuthenticationService authenticationService;

    @MockBean
    private transient BCryptPasswordEncoder bcpe;

    private transient Authentication authTa;
    private transient Authentication authLecturer;
    private transient Authentication authAdmin;
    private transient String findAllResult;
    private transient Role role;
    private transient String courseId = "CSE1200";
    private transient String studentId = "kvasilev";

    @MockBean
    private transient MockHttpServletRequest request;
    @Autowired
    private transient WebApplicationContext context;

    @BeforeEach
    void setUp() {
        role = new Role("ROLE_lecturer");
        authTa = new Authentication("net2@id.nl", "pass2", "name",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_ta"))));
        authLecturer = new Authentication("net2@id.nl", "pass2", "name",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_lecturer"))));
        authAdmin = new Authentication("net2@id.nl", "pass2", "name",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_admin"))));
        findAllResult = "{\"id\":1,\"courseId\":CSE1200,\"studentId\":kvasilev,\"amountOfHours\""
                + ":120.0,\"approvedHours\":50.0,\"declaredHours\":20.0,\"rating\":10.0}";

        this.mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

    }



    @Test
    @WithMockUser(roles = "admin")
    void saveUserTest() {
        when(authenticationService.saveAuth(authTa)).thenReturn(authTa);
        assertEquals(authTa, authenticationController.saveUser(authTa));
    }

    @Test
    @WithMockUser(roles = "admin")
    void saveRoleTest() {
        when(authenticationService.saveRole(role)).thenReturn(role);
        assertEquals(role, authenticationController.saveRole(role));
    }

    @Test
    @WithMockUser(roles = "admin")
    void addRoleToUserTest() {
        when(authenticationService.addRoleToAuthentication("net1@id.nl", "ROLE_student")).thenReturn("Role added successfully");
        assertEquals("Role added successfully", authenticationController.addRoleToUser("net1@id.nl", "ROLE_student"));
    }

    @Test
    @WithMockUser(roles = "admin")
    void getMicroserviceWithAuthenticationAdmin() throws Exception {
        when(serverCommunication.getRequest("/management/findAll"))
                .thenReturn(findAllResult);
        mvc.perform(get("/management/findAll"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "admin")
    void getMicroserviceWithAuthenticationAdminCheckContent() throws Exception {
        Mockito.when(serverCommunication.getRequest("/management/findAll"))
                .thenReturn(findAllResult);
        mvc.perform(get("http://localhost:8081/management/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().string(findAllResult));
    }

    @Test
    @WithMockUser(roles = "admin")
    void putMicroserviceRate() throws Exception {
        Mockito.when(serverCommunication
                .putRequest("/management/rate?courseId=CSE1200&studentId=kvasilev&rating=8", ""))
                .thenReturn("success");
        mvc.perform(put("http://localhost:8081/management/rate?courseId=CSE1200&studentId=kvasilev&rating=8"))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
    }

    @Test
    @WithMockUser(roles = "admin")
    void putMicroserviceRateWithBody() throws Exception {
        Mockito.when(serverCommunication
                .putRequest("/management/rate?courseId=CSE1200&studentId=kvasilev&rating=8",
                        findAllResult))
                .thenReturn(findAllResult);
        mvc.perform(put("http://localhost:8081/management/rate?courseId=CSE1200&studentId=kvasilev&rating=8")
                .content(findAllResult))
                .andExpect(status().isOk())
                .andExpect(content().string(findAllResult));
    }

    @Test
    @WithMockUser(roles = "admin")
    void postMicroserviceRate() throws Exception {
        Mockito.when(serverCommunication
                .postRequest("/management/rate?courseId=CSE1200&studentId=kvasilev&rating=8", ""))
                .thenReturn(findAllResult);
        mvc.perform(post("http://localhost:8081/management/rate?courseId=CSE1200&studentId=kvasilev&rating=8"))
                .andExpect(status().isOk())
                .andExpect(content().string(findAllResult));
    }

    @Test
    @WithMockUser(roles = "admin")
    void postMicroserviceRateWithBody() throws Exception {
        Mockito.when(serverCommunication
                .postRequest("/management/rate?courseId=CSE1200&studentId=kvasilev&rating=8",
                        findAllResult))
                .thenReturn(findAllResult);
        mvc.perform(post("http://localhost:8081/management/rate?courseId=CSE1200&studentId=kvasilev&rating=8")
                .content(findAllResult))
                .andExpect(status().isOk())
                .andExpect(content().string(findAllResult));
    }
}
