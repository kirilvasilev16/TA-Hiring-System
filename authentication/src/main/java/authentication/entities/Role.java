package authentication.entities;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;
    private String name;

    /**
     * No argument constructor.
     */
    public Role() {}

    /**
     * role constructor.
     *
     * @param name of role
     */
    public Role(String name) {
        this.name = name;
    }
    /**
     * name getter.
     *
     * @return id of role
     */
    public String getName() {
        return name;
    }

    /**
     * name setter.
     */
    public void setName(String name) {
        this.name = name;
    }
}
