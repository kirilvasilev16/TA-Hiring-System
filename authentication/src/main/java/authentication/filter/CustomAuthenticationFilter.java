package authentication.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final transient AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * attempts authentication using authenticationManager - spring security functionality.
     *
     * @param request from user to login
     * @param response response being created
     * @return an Authentication object
     * @throws AuthenticationException if user cannot be authenticated
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        String netId = request.getParameter("netid");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(netId, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * in the case of a successful authentication, JWT token is created using this function.
     *
     * @param request from user
     * @param response that will be sent
     * @param chain of filters that we have, for authentication and authorization
     * @param authentication Authentication object of Spring Security
     * @throws IOException io exception
     * @throws ServletException servlet exception
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication authentication)
            throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        //unsafe ask
        Algorithm algorithm = Algorithm.HMAC256("sem15a".getBytes());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

       Map<String, String> tokens = new HashMap<>();
       tokens.put("access_token", accessToken);
       tokens.put("refresh_token", refreshToken);
       response.setContentType(APPLICATION_JSON_VALUE);
       new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
