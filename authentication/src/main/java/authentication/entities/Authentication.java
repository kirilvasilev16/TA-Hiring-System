package authentication.entities;

import static javax.persistence.GenerationType.AUTO;

import java.util.Collection;
import javax.persistence.*;


@Entity(name = "Authentication")
public class Authentication {
    @Id
    private String netId;
    private String password;
    private String email;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Role> roles;

    public Authentication(){}

    /**
     * Authentication object constructor.
     *
     * @param netId of the user
     * @param password of the user
     * @param name of the user
     * @param roles granted for the user like student/ta/admin/lecturer
     */
    public Authentication(String netId, String email, String password, String name, Collection<Role> roles) {
        this.netId = netId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    /**
     * password getter.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * password setter.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * roles getter.
     *
     * @return roles
     */
    public Collection<Role> getRoles() {
        return roles;
    }

    /**
     * roles setter.
     */
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    /**
     * netId getter.
     *
     * @return netId
     */
    public String getNetId() {
        return netId;
    }

    /**
     * netID setter.
     */
    public void setNetId(String netId) {
        this.netId = netId;
    }

    /**
     * name getter.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * name setter.
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * email getter.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * email setter.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
