package lecturer.entities;

import java.util.List;
import java.util.Objects;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Lecturer {
    @Id
    private String netId;
    private String name;
    private String email;

    @ElementCollection
    private List<String> courses;

    /**
     * Constructor.
     *
     * @param netId of a lecturer
     * @param name name
     * @param email email
     * @param courses list of courses
     */
    public Lecturer(
            String netId,
            String name,
            String email,
            List<String> courses) {
        this.netId = netId;
        this.name = name;
        this.email = email;
        this.courses = courses;
    }

    /**
     * Empty constructor.
     */
    public Lecturer() {}

    /**
     * Getter for netId.
     *
     * @return netId
     */
    public String getNetId() {
        return netId;
    }

    /**
     * Getter for name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for courses.
     *
     * @return courses
     */
    public List<String> getCourses() {
        return courses;
    }

    /**
     * Setter for netId.
     *
     * @param netId of a lecturer
     */
    public void setNetId(String netId) {
        this.netId = netId;
    }

    /**
     * Setter for name.
     *
     * @param name of a lecturer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for an email.
     *
     * @param email of a lecturer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter for courses.
     *
     * @param courses of a lecturer
     */
    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lecturer lecturer = (Lecturer) o;
        return Objects.equals(netId, lecturer.netId)
                && Objects.equals(name, lecturer.name)
                && Objects.equals(email, lecturer.email)
                && Objects.equals(courses, lecturer.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(netId, name, email, courses);
    }
}
