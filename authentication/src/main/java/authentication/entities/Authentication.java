package authentication.entities;

import java.util.Collection;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
    public Authentication(String netId, String email,
                          String password, String name, Collection<Role> roles) {
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

    /**
     * Equals method for Authentication.
     *
     * @param o other object
     * @return true iff the 2 objects are identical
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Authentication that = (Authentication) o;
        return netId == that.netId
                && email == that.email
                && password == that.password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNetId(), getPassword(), getEmail(), getName(), getRoles());
    }
}
