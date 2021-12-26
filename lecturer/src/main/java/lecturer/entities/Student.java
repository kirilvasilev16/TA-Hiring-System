package lecturer.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Student {
    private String netId;
    private String name;
    private Map<String, Float> passedCourses;
    private Set<String> candidateCourses;
    private Set<String> taCourses;

    /**
     * Basic constructor.
     *
     * @param id of student
     */
    public Student(String id) {
        this.netId = id;
        this.passedCourses = new HashMap<>();
        this.candidateCourses = new HashSet<>();
        this.taCourses = new HashSet<>();
    }

    public Student() {
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getNetId() {
        return netId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Float> getPassedCourses() {
        return passedCourses;
    }

    public void setPassedCourses(Map<String, Float> passedCourses) {
        this.passedCourses = passedCourses;
    }

    public Set<String> getCandidateCourses() {
        return candidateCourses;
    }

    public void setCandidateCourses(Set<String> candidateCourses) {
        this.candidateCourses = candidateCourses;
    }

    public Set<String> getTaCourses() {
        return taCourses;
    }

    public void setTaCourses(Set<String> taCourses) {
        this.taCourses = taCourses;
    }
}
