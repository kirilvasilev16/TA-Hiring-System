package authentication.filter;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    /**
     * used for authorization, absorbs the token and checks if
     * the token is valid and what roles it has.
     *
     * @param request     of user
     * @param response    that will be sent to the user
     * @param filterChain chain of filters that are applied to the user
     * @throws ServletException servlet exception
     * @throws IOException      IO exception
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getServletPath().equals("/login")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null
                    && authorizationHeader.startsWith("Bearer ")) {
                try {
                    DecodedJWT decodedJwt = getDecodedJwt(authorizationHeader);
                    decodeAndCreateAuthToken(decodedJwt);

                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    errorResponse(response, e);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    /**
     * reacts to error when JWT token cannot be created.
     *
     * @param response of the server
     * @param e exception
     * @throws IOException throw IOException
     */
    public void errorResponse(HttpServletResponse response, Exception e) throws IOException {
        response.setHeader("error", e.getMessage());
        response.setStatus(FORBIDDEN.value());

        Map<String, String> error = new HashMap<>();
        error.put("error_message", e.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper()
                .writeValue(response.getOutputStream(),
                        error);
    }

    /**
     * get the decoded version of JWT to extract information.
     *
     * @param authorizationHeader "Bearer ey..."
     * @return DecodedJWT object
     */
    public DecodedJWT getDecodedJwt(String authorizationHeader) {
        String token =
                authorizationHeader.substring(
                        "Bearer ".length());

        Algorithm algorithm =
                Algorithm.HMAC256("sem15a".getBytes());
        JWTVerifier verifier =
                JWT.require(algorithm).build();
        DecodedJWT decodedJwt =
                verifier.verify(token);
        return decodedJwt;
    }

    /**
     * sets security context and creates authentication token.
     *
     * @param decodedJwt decoded JWT token
     */
    public void decodeAndCreateAuthToken(DecodedJWT decodedJwt) {
        String netId = decodedJwt.getSubject();
        String[] roles = decodedJwt.getClaim("roles")
                .asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities =
                new ArrayList<>();
        stream(roles).forEach(role -> authorities
                .add(new SimpleGrantedAuthority(role)));
        UsernamePasswordAuthenticationToken
                authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        netId, null, authorities);

        SecurityContextHolder.getContext()
                .setAuthentication(authenticationToken);
    }
}
