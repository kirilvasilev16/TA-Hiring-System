package authentication.communication;

import org.springframework.security.core.context.SecurityContext;

public class SecurityContextHolder {
    private static SecurityContext securityContext = org.springframework.security
            .core.context.SecurityContextHolder.getContext();

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext() {
        securityContext = org.springframework.security
                .core.context.SecurityContextHolder.getContext();
    }
}
