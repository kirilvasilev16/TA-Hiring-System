package authentication.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import authentication.filter.CustomAuthorizationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomAuthorizationFilterTest {

    @Mock
    private transient JWTVerifier jwtVerifier;
    @MockBean
    private transient Algorithm algorithm;

    @MockBean
    private transient DecodedJWT mockJwt;

    private transient CustomAuthorizationFilter customAuthorizationFilter;
    private transient String token;
    private transient String expiredToken;
    private transient String authorizationHeader;
    private transient DecodedJWT decodedJwt;
    @MockBean
    private transient String[] roles;
    private transient Collection<SimpleGrantedAuthority> authorities =
            new ArrayList<>();

    @BeforeEach
    void setUp() {
        roles = new String[1];
        roles[0] = "ROLE_student";
        customAuthorizationFilter = new CustomAuthorizationFilter();
        algorithm = Algorithm.HMAC256("sem15a".getBytes());
        token = JWT.create()
                .withSubject("netId")
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer("issuer")
                .withClaim("roles", new HashSet<GrantedAuthority>()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
        expiredToken = JWT.create()
                .withSubject("netId")
                .withExpiresAt(new Date(System.currentTimeMillis() - 10 * 60 * 1000))
                .withIssuer("issuer")
                .withClaim("roles", new HashSet<GrantedAuthority>()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
        authorizationHeader = "Bearer " + token;
        expiredToken = "Bearer " + expiredToken;
        //jwtVerifier = JWT.require(Algorithm.HMAC256("sem15a".getBytes())).build();
        jwtVerifier = Mockito.mock(JWTVerifier.class);
        decodedJwt = new DecodedJWT() {
            @Override
            public String getToken() {
                return token;
            }

            @Override
            public String getHeader() {
                return null;
            }

            @Override
            public String getPayload() {
                return null;
            }

            @Override
            public String getSignature() {
                return null;
            }

            @Override
            public String getAlgorithm() {
                return null;
            }

            @Override
            public String getType() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public String getKeyId() {
                return null;
            }

            @Override
            public Claim getHeaderClaim(String name) {
                return null;
            }

            @Override
            public String getIssuer() {
                return null;
            }

            @Override
            public String getSubject() {
                return "netId";
            }

            @Override
            public List<String> getAudience() {
                return null;
            }

            @Override
            public Date getExpiresAt() {
                return null;
            }

            @Override
            public Date getNotBefore() {
                return null;
            }

            @Override
            public Date getIssuedAt() {
                return null;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public Claim getClaim(String name) {
                return null;
            }

            @Override
            public Map<String, Claim> getClaims() {
                return null;
            }
        };

        mockJwt = Mockito.mock(DecodedJWT.class);

    }

    @Test
    void getDecodedJwtTestException() {
        assertThrows(TokenExpiredException.class,
                () -> customAuthorizationFilter.getDecodedJwt(expiredToken));
    }

    @Test
    void getDecodedJwtTest() {
        doReturn(decodedJwt).when(jwtVerifier).verify(token);
        assertEquals(customAuthorizationFilter
                .getDecodedJwt(authorizationHeader).getToken(), token);
    }

}
