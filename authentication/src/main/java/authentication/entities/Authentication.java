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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
