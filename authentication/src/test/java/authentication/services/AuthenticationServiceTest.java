package authentication.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import authentication.entities.Authentication;
import authentication.entities.Role;
import authentication.repository.AuthenticationRepository;
import authentication.repository.RoleRepository;
import authentication.service.AuthenticationService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
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
    private transient List<Authentication> list;

    @BeforeEach
    void setup() {
        roleRepository = Mockito.mock(RoleRepository.class);
        authenticationRepository = Mockito.mock(AuthenticationRepository.class);
        authenticationService = new AuthenticationService(authenticationRepository,
                roleRepository);
        role = new Role("ROLE_ta");

        testStudent = new Authentication("stu@id.nl", "email@email.co", "stupass", "password1",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_student"))));
        testLecturer = new Authentication("lec@id.nl", "email@email.co", "lecpass", "password2",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_lecturer"))));
        testTa = new Authentication("ta@id.nl", "email@email.co", "modpass", "password3",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_ta"))));
        testAdmin = new Authentication("adm@id.nl", "email@email.co", "admpass", "password4",
                new ArrayList<Role>(Arrays.asList(new Role("ROLE_admin"))));
        testTaNoRole = new Authentication("stu@id.nl", "email@email.co", "stupass", "password5",
                new ArrayList<>());

        list = new ArrayList<>(Arrays.asList(testStudent, testTa, testLecturer, testAdmin));
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

    @Test
    void loadUserByUsernameException() {
        Mockito.when(authenticationRepository.findByNetId("stu")).thenReturn(null);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        assertThrows(UsernameNotFoundException.class,
                () -> authenticationService.loadUserByUsername("hello"));
    }

}
