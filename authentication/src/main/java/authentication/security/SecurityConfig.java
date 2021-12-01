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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final transient UserDetailsService userDetailsService;
    private final transient BCryptPasswordEncoder bcpe;

    public SecurityConfig(UserDetailsService userDetailsService,
                          BCryptPasswordEncoder bcpe) {
        this.userDetailsService = userDetailsService;
        this.bcpe = bcpe;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("AuthManagerBuilder config");
        auth.userDetailsService(userDetailsService).passwordEncoder(bcpe);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("HttpSecurityConfig config");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests()
                .antMatchers(GET, "/auth/getAll").hasAnyAuthority("ROLE_ta");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        System.out.println("authentication manager config");
        return super.authenticationManagerBean();
    }
}
