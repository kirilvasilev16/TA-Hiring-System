package student.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The type Student.
 */
@Entity(name = "Student")
public class Student {

    @Id
    @Column(name = "id")
    private String netId;

    @Column(name = "name")
    private String name;

    @ElementCollection
    @Column(name = "passedCourses")
    private Map<String, Float> passedCourses;

    @ElementCollection
    @Column(name = "candidateCourses")
    private Set<String> candidateCourses;

    @ElementCollection
    @Column(name = "taCourses")
    private Set<String> taCourses;

    /**
     * Instantiates a new Student.
     *
     * @param netId the net id
     * @param name  the name
     */
    public Student(String netId, String name) {
        this.netId = netId;
        this.name = name;
        this.passedCourses = new HashMap<>();
        this.candidateCourses = new HashSet<>();
        this.taCourses = new HashSet<>();
    }

    /**
     * Default constructor.
     */
    public Student() {

    }

    /**
     * Gets net id.
     *
     * @return the net id
     */
    public String getNetId() {
        return netId;
    }

    /**
     * Sets net id.
     *
     * @param id the id
     */
    public void setNetId(String id) {
        this.netId = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets candidate courses.
     *
     * @return the candidate courses
     */
    public Set<String> getCandidateCourses() {
        return candidateCourses;
    }

    /**
     * Sets candidate courses.
     *
     * @param candidateCourses the candidate courses
     */
    public void setCandidateCourses(Set<String> candidateCourses) {
        this.candidateCourses = candidateCourses;
    }

    /**
     * Gets ta courses.
     *
     * @return the ta courses
     */
    public Set<String> getTaCourses() {
        return taCourses;
    }

    /**
     * Sets ta courses.
     *
     * @param taCourses the ta courses
     */
    public void setTaCourses(Set<String> taCourses) {
        this.taCourses = taCourses;
    }

    /**
     * Gets passed courses.
     *
     * @return the passed courses
     */
    public Map<String, Float> getPassedCourses() {
        return passedCourses;
    }

    /**
     * Sets passed courses.
     *
     * @param passedCourses the passed courses
     */
    public void setPassedCourses(Map<String, Float> passedCourses) {
        this.passedCourses = passedCourses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(getNetId(), student.getNetId())
                && Objects.equals(getName(), student.getName())
                && Objects.equals(getPassedCourses(), student.getPassedCourses())
                && Objects.equals(getCandidateCourses(), student.getCandidateCourses())
                && Objects.equals(getTaCourses(), student.getTaCourses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNetId(), getName(), getPassedCourses(),
                getCandidateCourses(), getTaCourses());
    }
}
