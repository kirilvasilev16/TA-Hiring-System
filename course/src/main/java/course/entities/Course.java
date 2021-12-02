package course.entities;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    private String courseID;
    private String name;
    private Integer courseSize;
    private Integer requiredTAs;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startingDate;

    @ElementCollection
    private Set<String> lecturerSet;
    @ElementCollection
    private Set<String> candidateTAs;
    @ElementCollection
    private Set<String> hiredTAs;

    /**
     * Constructor for Course object
     * @param courseID String courseID
     * @param name String course name
     * @param courseSize int course student size
     * @param lecturerSet et of strings where strings are lecturerIDs
     * @param startingDate Date course start date
     */
    public Course(String courseID, String name, int courseSize,Set<String> lecturerSet, Date startingDate) {
        this.courseID = courseID;
        this.name = name;
        this.startingDate = startingDate;
        this.courseSize = courseSize;
        this.requiredTAs = Math.max(1, courseSize / 20);

        this.lecturerSet = lecturerSet;
        this.candidateTAs = new HashSet<>();
        this.hiredTAs = new HashSet<>();
    }

    public Course() {

    }


    /**
     * Getter for courseID
     * @return String courseID
     */
    public String getCourseID() {
        return this.courseID;
    }

    /**
     * Setter for courseID
     * @param courseID String new courseID
     */
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    /**
     * Getter for course name
     * @return String course name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for course name
     * @param name String new course name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for course starting date
     * @return Date object
     */
    public Date getStartingDate() {
        return this.startingDate;
    }

    /**
     * Setter for course starting date
     * @param startingDate Date object new starting date
     */
    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    /**
     * Getter for course student size
     * @return int number of students
     */
    public int getCourseSize() {
        return this.courseSize;
    }

    /**
     * Setter for course student size
     * @param courseSize int new number of students
     */
    public void setCourseSize(Integer courseSize) {
        this.courseSize = courseSize;
        setRequiredTAs(Math.max(1, courseSize / 20));
    }


    /**
     * Getter for required TAs
     * @return int
     */
    public Integer getRequiredTAs() {
        return requiredTAs;
    }

    /**
     * Setter for required TAs
     * @param requiredTAs Integer number of required TAs
     */
    public void setRequiredTAs(Integer requiredTAs) {
        this.requiredTAs = requiredTAs;
    }

    /**
     * Getter for Lecturer set in course
     * @return Set of strings where strings are lecturerIDs
     */
    public Set<String> getLecturerSet() {
        return lecturerSet;
    }

    /**
     * Setter for Lecturer set in course
     * @param lecturerSet Set of strings where strings are lecturerIDs
     */
    public void setLecturerSet(Set<String> lecturerSet) {
        this.lecturerSet = lecturerSet;
    }

    /**
     * Getter for Candidate TA set in course
     * @return Set of strings where strings are studentIDs
     */
    public Set<String> getCandidateTAs() {
        return candidateTAs;
    }

    /**
     * Setter for Candidate TA set
     * @param candidateTAs Set of strings where strings are studentIDs
     */
    public void setCandidateTAs(Set<String> candidateTAs) {
        this.candidateTAs = candidateTAs;
    }

    /**
     * Getter for Hired TA set in course
     * @return Set of strings where strings are studentDIs
     */
    public Set<String> getHiredTAs() {
        return hiredTAs;
    }

    /**
     * Setter for Hired TA set
     * @param hiredTAs Set of strings where strings are studentIDs
     */
    public void setHiredTAs(Set<String> hiredTAs) {
        this.hiredTAs = hiredTAs;
    }
}
