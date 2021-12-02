package course.entities;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;
import java.util.Set;


public class Student {

    private String netId;
    private Map<String, Float> passedCourses;
    private Set<String> taCourses;

    public Student(String netId, Map<String, Float> passedCourses, Set<String> taCourses) {
        this.netId = netId;
        this.passedCourses = passedCourses;
        this.taCourses = taCourses;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public Map<String, Float> getPassedCourses() {
        return passedCourses;
    }

    public void setPassedCourses(Map<String, Float> passedCourses) {
        this.passedCourses = passedCourses;
    }

    public Set<String> getTaCourses() {
        return taCourses;
    }

    public void setTaCourses(Set<String> taCourses) {
        this.taCourses = taCourses;
    }
}
