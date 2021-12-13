package authentication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@Configuration
@EnableWebSecurity
public class  SecurityConfig extends WebSecurityConfigurerAdapter {

    private final transient UserDetailsService userDetailsService;
    private final transient BCryptPasswordEncoder bcpe;

    public SecurityConfig(UserDetailsService userDetailsService,
                          BCryptPasswordEncoder bcpe) {
        this.userDetailsService = userDetailsService;
        this.bcpe = bcpe;
    }

    /**
     * configure authenticationManager.
     *
     * @param auth uses userDetailsService.
     * @throws Exception if authentication manager cannot be configured
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("AuthManagerBuilder config");
        auth.userDetailsService(userDetailsService).passwordEncoder(bcpe);
    }

    /**
     * configure httpSecurity.
     *
     * @param http HttpSecurity object
     * @throws Exception if HttpSecurityConfig cannot be configurated.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("HttpSecurityConfig config");

        Validator loginHandler = new LoginValidator();
        Validator authenticationRoleHandler = new AuthenticationRoleValidator();
        Validator filterHandler = new FilterValidator(authenticationManagerBean());

        loginHandler.setNext(authenticationRoleHandler);
        authenticationRoleHandler.setNext(filterHandler);

        try {
            loginHandler.handle(http);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * creates bean for authentication management.
     *
     * @return AuthenticationManager object
     * @throws Exception if bean cannot be created
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        System.out.println("authentication manager config");
        return super.authenticationManagerBean();
    }
}
