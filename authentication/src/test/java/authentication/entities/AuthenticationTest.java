package authentication.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
class AuthenticationTest {
    private transient Authentication auth;
    private transient String netId = "net@id.nl";
    private transient String name = "name";
    private transient String email = "email";
    private transient String password = "password";

    @BeforeEach
    void setUp() {
        auth = new Authentication(netId, email, password, name, new ArrayList<>());
    }

    @Test
    void constructor() {
        assertNotNull(auth);
    }

    @Test
    void getPassword() {
        assertEquals(password, auth.getPassword());
    }

    @Test
    void setPassword() {
        auth.setPassword("myPassword");
        assertEquals("myPassword", auth.getPassword());
    }

    @Test
    void getRoles() {
        assertEquals(0, auth.getRoles().size());
    }

    @Test
    void setRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_ta"));
        auth.setRoles(roles);
        assertEquals(roles, auth.getRoles());
    }

    @Test
    void getNetId() {
        assertEquals(netId, auth.getNetId());
    }

    @Test
    void setNetId() {
        auth.setNetId("myId");
        assertEquals("myId", auth.getNetId());
    }

    @Test
    void getName() {
        assertEquals(name, auth.getName());
    }

    @Test
    void setName() {
        auth.setName("myName");
        assertEquals("myName", auth.getName());
    }

    @Test
    void getEmail() {
        assertEquals(email, auth.getEmail());
    }

    @Test
    void setEmail() {
        auth.setEmail("email2");
        assertEquals("email2", auth.getEmail());
    }

    @ParameterizedTest
    @MethodSource("generatorEquals")
    void equalsNot(Object management1) {
        assertNotEquals(auth, management1);
    }

    private static Stream<Arguments> generatorEquals() {

        Authentication wrongId = new Authentication("wrongNet", "email",
                "password", "name", new ArrayList<>());
        Authentication wrongEmail = new Authentication("net@id.nl", "email@email",
                "password", "name", new ArrayList<>());

        Authentication wrongPassword = new Authentication("net@id.nl", "email",
                "123456", "name", new ArrayList<>());
        Authentication nullManagement = null;
        return Stream.of(
                Arguments.of(nullManagement),
                Arguments.of(wrongId),
                Arguments.of(wrongEmail),
                Arguments.of(wrongPassword)
        );
    }

    @Test
    void equalsManagement() {
        Authentication management1 = new Authentication(netId, email,
                password, name, new ArrayList<>());
        assertEquals(auth, management1);
    }

    @Test
    void equalsSame() {
        assertEquals(auth, auth);
    }

    @Test
    void equalsDifferentClass() {
        assertNotEquals(auth, new Integer(4));
    }
}