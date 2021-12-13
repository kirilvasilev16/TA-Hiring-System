package authentication.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public abstract class BaseValidator implements Validator {

    private Validator next;

    public Validator getNext() {
        return next;
    }

    @Override
    public void setNext(Validator handler) {
        this.next = handler;
    }

    protected void checkNext(HttpSecurity http) throws Exception {
        if (next != null) {
            next.handle(http);
        }
    }

    public abstract void handle(HttpSecurity http) throws Exception;
}
