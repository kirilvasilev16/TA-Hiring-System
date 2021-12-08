package authentication.entities;

import static javax.persistence.GenerationType.AUTO;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity(name = "Authentication")
public class Authentication {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String netId;
    private String password;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
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
    public Authentication(String netId, String password, String name, Collection<Role> roles) {
        this.netId = netId;
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
     * id getter.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * id setter.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
