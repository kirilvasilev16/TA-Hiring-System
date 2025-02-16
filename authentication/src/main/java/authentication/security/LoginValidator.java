package authentication.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

public class LoginValidator extends BaseValidator {

    /**
     * disable crfs and permit login to all users.
     *
     * @param http configures security
     */
    @Override
    public void handle(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login").permitAll();
        super.checkNext(http);
    }
}
