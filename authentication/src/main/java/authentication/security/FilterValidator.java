package authentication.security;

import authentication.filter.CustomAuthenticationFilter;
import authentication.filter.CustomAuthorizationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class FilterValidator extends BaseValidator {

    private AuthenticationManager authenticationManager;

    public FilterValidator(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * add the filters for authentication and authorization.
     *
     * @param http configures security
     */
    @Override
    public void handle(HttpSecurity http) {
        http.addFilter(new CustomAuthenticationFilter(authenticationManager));
        http.addFilterBefore(new CustomAuthorizationFilter(),
                UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * getter for authentication manager.
     *
     * @return authenticationManager
     */
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    /**
     * setter for authenticationManager.
     *
     * @param authenticationManager new authenticationManager
     */
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}
