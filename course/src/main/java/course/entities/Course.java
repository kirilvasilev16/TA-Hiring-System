package course.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    private String courseID;
    private String name;
    private Integer courseSize;
    @OneToMany
    private Set<Lecturer> lecturerSet;
    private Integer requiredTAs;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startingDate;
    @OneToMany
    private List<Student> candidateTAs;
    @OneToMany
    private List<Management> hiredTAs;

    public Course(String courseID, String name, Set<Lecturer> lecturerSet, Date startingDate) {
        this.courseID = courseID;
        this.name = name;
        this.lecturerSet = lecturerSet;
        this.startingDate = startingDate;
        courseSize = 0;
        requiredTAs = 0;
        candidateTAs = new ArrayList<>();
        hiredTAs = new ArrayList<>();
    }

    public Course() {

    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCourseSize() {
        return courseSize;
    }

    public void setCourseSize(Integer courseSize) {
        this.courseSize = courseSize;
    }

    public Set<Lecturer> getLecturerSet() {
        return lecturerSet;
    }

    public void setLecturerSet(Set<Lecturer> lecturerSet) {
        this.lecturerSet = lecturerSet;
    }

    public Integer getRequiredTAs() {
        return requiredTAs;
    }

    public void setRequiredTAs(Integer requiredTAs) {
        this.requiredTAs = requiredTAs;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public List<Student> getCandidateTAs() {
        return candidateTAs;
    }

    public void setCandidateTAs(List<Student> candidateTAs) {
        this.candidateTAs = candidateTAs;
    }

    public List<Management> getHiredTAs() {
        return hiredTAs;
    }

    public void setHiredTAs(List<Management> hiredTAs) {
        this.hiredTAs = hiredTAs;
    }
}
