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

    /**
     * Empty constructor.
     */
    public Student() {
    }

    /**
     * Setter for id.
     *
     * @param netId id of a student
     */
    public void setNetId(String netId) {
        this.netId = netId;
    }

    /**
     * Getter for id.
     *
     * @return net id
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
     * Setter for name.
     *
     * @param name name of a student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for passed courses.
     *
     * @return map of passed courses
     */
    public Map<String, Float> getPassedCourses() {
        return passedCourses;
    }

    /**
     * Setter for passed courses.
     *
     * @param passedCourses map of passed courses with grade of a student
     */
    public void setPassedCourses(Map<String, Float> passedCourses) {
        this.passedCourses = passedCourses;
    }

    /**
     * Getter for candidate courses.
     *
     * @return set of candidate courses
     */
    public Set<String> getCandidateCourses() {
        return candidateCourses;
    }

    /**
     * Setter for candidate courses.
     *
     * @param candidateCourses set of candidate courses of a student
     */
    public void setCandidateCourses(Set<String> candidateCourses) {
        this.candidateCourses = candidateCourses;
    }

    /**
     * Getter for ta courses.
     *
     * @return set of ta courses
     */
    public Set<String> getTaCourses() {
        return taCourses;
    }

    /**
     * Setter for ta courses.
     *
     * @param taCourses set of ta courses of a student
     */
    public void setTaCourses(Set<String> taCourses) {
        this.taCourses = taCourses;
    }
}
