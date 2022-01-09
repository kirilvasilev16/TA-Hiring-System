package authentication.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public abstract class BaseValidator implements Validator {

    private Validator next;

    public Validator getNext() {
        return next;
    }

    /**
     * set the next validator.
     *
     * @param handler the next element in the chain of responsibilities
     */
    @Override
    public void setNext(Validator handler) {
        this.next = handler;
    }

    /**
     * checks if there is another responsibility afterwards.
     *
     * @param http security config object
     * @throws Exception if next.handle goes wrong
     */
    public void checkNext(HttpSecurity http) throws Exception {
        if (next != null) {
            next.handle(http);
        }
    }

    /**
     * will handle the responsibilities.
     *
     * @param http configures security
     * @throws Exception if exception does not work.
     */
    public abstract void handle(HttpSecurity http) throws Exception;
}
