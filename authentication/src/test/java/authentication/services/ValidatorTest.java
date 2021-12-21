package authentication.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import authentication.security.BaseValidator;
import authentication.security.FilterValidator;
import authentication.security.LoginValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class ValidatorTest {

    @Autowired
    private transient LoginValidator validator = new LoginValidator();

    @Autowired
    private transient BaseValidator nextValidator = new LoginValidator();

    @MockBean
    private transient HttpSecurity http;

    @MockBean
    private transient AuthenticationManager authenticationManager;

    private transient FilterValidator filterValidator;

    @BeforeEach
    void setup() {
        validator.setNext(nextValidator);
        nextValidator.setNext(null);
        filterValidator = new FilterValidator(authenticationManager);
    }

    @Test
    void validatorGetNext() {
        assertEquals(nextValidator, validator.getNext());
    }

    @Test
    void validatorGetNextNull() {
        assertNull(nextValidator.getNext());
    }

    @Test
    void getAuthenticationManager() {
        assertEquals(authenticationManager, filterValidator.getAuthenticationManager());
    }

    @Test
    void setAuthenticationManager() {
        AuthenticationManager authenticationManager = authentication -> null;
        filterValidator.setAuthenticationManager(authenticationManager);
        assertEquals(authenticationManager, filterValidator.getAuthenticationManager());
    }

}
