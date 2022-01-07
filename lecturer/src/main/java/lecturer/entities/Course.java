package lecturer.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public class Course {
    @JsonProperty("courseId")
    private String courseId;
    @JsonProperty("courseSize")
    private int courseSize;
    @JsonProperty("candidateTas")
    private Set<String> candidateTas;
    @JsonProperty("hiredTas")
    private Set<String> hiredTas;

    /**
     * Empty constructor.
     */
    public Course() {
    }

    /**
     * Constructor.
     *
     * @param courseId course id
     * @param candidateTas candidates TAs
     * @param courseSize size of course
     */
    public Course(String courseId, Set<String> candidateTas, int courseSize) {
        this.courseId = courseId;
        this.candidateTas = candidateTas;
        this.courseSize = courseSize;
    }

    /**
     * Getter for course id.
     *
     * @return course id
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Setter for course id.
     *
     * @param courseId course id
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Getter for course size.
     *
     * @return course size
     */
    public int getCourseSize() {
        return courseSize;
    }

    /**
     * Setter for course size.
     *
     * @param courseSize course size
     */
    public void setCourseSize(int courseSize) {
        this.courseSize = courseSize;
    }

    /**
     * Getter for candidates.
     *
     * @return candidate tas
     */
    public Set<String> getCandidateTas() {
        return candidateTas;
    }

    /**
     * Setter for candidates.
     *
     * @param candidateTas candidates
     */
    public void setCandidateTas(Set<String> candidateTas) {
        this.candidateTas = candidateTas;
    }

    /**
     * Getter for hired tas.
     *
     * @return hired tas
     */
    public Set<String> getHiredTas() {
        return hiredTas;
    }

    /**
     * Setter for hired tas.
     *
     * @param hiredTas hired tas
     */
    public void setHiredTas(Set<String> hiredTas) {
        this.hiredTas = hiredTas;
    }
}
