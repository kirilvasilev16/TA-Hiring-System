package authentication.security;

import static org.springframework.http.HttpMethod.GET;

import authentication.filter.CustomAuthenticationFilter;
import authentication.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests()
                .antMatchers(GET, "/authentication/**").hasAnyAuthority("ROLE_admin");
        http.authorizeRequests()
                .antMatchers(GET, "/student/**").hasAnyAuthority("ROLE_student", "ROLE_ta");
        http.authorizeRequests()
                .antMatchers(GET, "/lecturer/**").hasAnyAuthority("ROLE_lecturer");
        http.authorizeRequests()
                .antMatchers(GET, "/courses/**").hasAnyAuthority("ROLE_admin");
        http.authorizeRequests()
                .antMatchers(GET, "/management/**").hasAnyAuthority("ROLE_lecturer", "ROLE_admin");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(),
                UsernamePasswordAuthenticationFilter.class);
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
