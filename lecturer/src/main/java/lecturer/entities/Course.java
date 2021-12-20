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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getCourseSize() {
        return courseSize;
    }

    public void setCourseSize(int courseSize) {
        this.courseSize = courseSize;
    }

    public Set<String> getCandidateTas() {
        return candidateTas;
    }

    public void setCandidateTas(Set<String> candidateTas) {
        this.candidateTas = candidateTas;
    }

    public Set<String> getHiredTas() {
        return hiredTas;
    }

    public void setHiredTas(Set<String> hiredTas) {
        this.hiredTas = hiredTas;
    }
}
