package authentication.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import authentication.entities.Authentication;
import authentication.entities.Role;
import authentication.repository.AuthenticationRepository;
import authentication.repository.RoleRepository;
import authentication.service.AuthenticationService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AuthenticationServiceTest {

    private transient AuthenticationService authenticationService;
    private transient AuthenticationRepository authenticationRepository;
    private transient RoleRepository roleRepository;

    transient Authentication testAdmin;
    transient Authentication testTa;
    transient Authentication testLecturer;
    transient Authentication testStudent;
    transient Authentication testTaNoRole;
    transient Role role;

    @BeforeEach
    void setup() {
        roleRepository = Mockito.mock(RoleRepository.class);
        authenticationRepository = Mockito.mock(AuthenticationRepository.class);
        authenticationService = new AuthenticationService(authenticationRepository, roleRepository);
        role = new Role("ROLE_ta");

        testStudent = new Authentication("stu@id.nl", "stupass", "password1",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_student"))));
        testLecturer = new Authentication("lec@id.nl", "lecpass", "password2",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_lecturer"))));
        testTa = new Authentication("ta@id.nl", "modpass", "password3",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_ta"))));
        testAdmin = new Authentication("adm@id.nl", "admpass", "password4",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_admin"))));
        testTaNoRole = new Authentication("stu@id.nl", "stupass", "password5",
                new ArrayList<>());
    }

    @Test
    void findAll() {
        Mockito.when(authenticationRepository.findAll())
                .thenReturn(Arrays.asList(testStudent, testTa, testLecturer, testAdmin));
        assertEquals(authenticationService.findAll(), Arrays.asList(testStudent,
                testTa, testLecturer, testAdmin));
    }

    @Test
    void saveRole() {
        Mockito.when(roleRepository.save(role)).thenReturn(role);
        assertEquals(authenticationService.saveRole(role), role);
    }

    @Test
    void saveAuth() {
        Mockito.when(authenticationRepository.save(testTa)).thenReturn(testTa);
        assertEquals(authenticationService.saveAuth(testTa), testTa);
    }

    @Test
    void addRoleToAuthentication() {
        Mockito.when(authenticationRepository.findByNetId("ta@id.nl")).thenReturn(testTa);
        Mockito.when(roleRepository.findByName("ROLE_ta")).thenReturn(
                new Role("ROLE_student"));
        assertEquals(authenticationService.addRoleToAuthentication("ta@id.nl",
                "ROLE_student"), "Role added successfully");
    }

    @Test
    void loadUserByUsername() {
        Mockito.when(authenticationRepository.findByNetId("stu")).thenReturn(testStudent);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        assertEquals(authenticationService.loadUserByUsername("stu"),
                new org.springframework.security
                        .core.userdetails.User(testStudent.getNetId(),
                            testStudent.getPassword(), authorities));
    }
}
