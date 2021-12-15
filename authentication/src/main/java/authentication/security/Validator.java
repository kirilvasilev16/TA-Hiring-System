package authentication.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface Validator {

    void setNext(Validator handler);

    void handle(HttpSecurity http) throws Exception;
}
