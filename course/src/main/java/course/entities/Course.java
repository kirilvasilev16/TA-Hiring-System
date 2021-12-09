package course.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    private String courseId;
    private String name;
    private Integer courseSize;
    private Integer requiredTas;
    private Integer quarter;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar startingDate;

    @ElementCollection
    private Set<String> lecturerSet;
    @ElementCollection
    private Set<String> candidateTas;
    @ElementCollection
    private Set<String> hiredTas;

    /**
     * Constructor for Course object.
     *
     * @param courseId     String courseID
     * @param name         String course name
     * @param courseSize   int course student size
     * @param lecturerSet  set of strings where strings are lecturerIDs
     * @param startingDate Calendar object course start date
     */
    public Course(String courseId, String name, int courseSize,
                  Set<String> lecturerSet, Calendar startingDate, Integer quarter) {
        this.courseId = courseId;
        this.name = name;
        this.startingDate = startingDate;
        this.courseSize = courseSize;
        this.requiredTas = Math.max(1, courseSize / 20);

        this.lecturerSet = lecturerSet;
        this.candidateTas = new HashSet<>();
        this.hiredTas = new HashSet<>();
        this.quarter = quarter;
    }

    public Course() {

    }


    /**
     * Getter for courseID.
     *
     * @return String courseID
     */
    public String getCourseId() {
        return this.courseId;
    }

    /**
     * Setter for courseId.
     *
     * @param courseId String new courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Getter for course name.
     *
     * @return String course name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for course name.
     *
     * @param name String new course name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for course starting date.
     *
     * @return Calendar object
     */
    public Calendar getStartingDate() {
        return this.startingDate;
    }

    /**
     * Setter for course starting date.
     *
     * @param startingDate Date object new starting date
     */
    public void setStartingDate(Calendar startingDate) {
        this.startingDate = startingDate;
    }

    /**
     * Getter for course student size.
     *
     * @return int number of students
     */
    public int getCourseSize() {
        return this.courseSize;
    }

    /**
     * Setter for course student size.
     *
     * @param courseSize int new number of students
     */
    public void setCourseSize(Integer courseSize) {
        this.courseSize = courseSize;
        setRequiredTas(Math.max(1, courseSize / 20));
    }


    /**
     * Getter for required TAs.
     *
     * @return int
     */
    public Integer getRequiredTas() {
        return requiredTas;
    }

    /**
     * Setter for required TAs.
     *
     * @param requiredTas Integer number of required TAs
     */
    public void setRequiredTas(Integer requiredTas) {
        this.requiredTas = requiredTas;
    }

    /**
     * Getter for Lecturer set in course.
     *
     * @return Set of strings where strings are lecturerIDs
     */
    public Set<String> getLecturerSet() {
        return lecturerSet;
    }

    /**
     * Setter for Lecturer set in course.
     *
     * @param lecturerSet Set of strings where strings are lecturerIDs
     */
    public void setLecturerSet(Set<String> lecturerSet) {
        this.lecturerSet = lecturerSet;
    }

    /**
     * Getter for Candidate TA set in course.
     *
     * @return Set of strings where strings are studentIDs
     */
    public Set<String> getCandidateTas() {
        return candidateTas;
    }

    /**
     * Setter for Candidate TA set.
     *
     * @param candidateTas Set of strings where strings are studentIDs
     */
    public void setCandidateTas(Set<String> candidateTas) {
        this.candidateTas = candidateTas;
    }

    /**
     * Getter for Hired TA set in course.
     *
     * @return Set of strings where strings are studentDIs
     */
    public Set<String> getHiredTas() {
        return hiredTas;
    }

    /**
     * Setter for Hired TA set.
     *
     * @param hiredTas Set of strings where strings are studentIDs
     */
    public void setHiredTas(Set<String> hiredTas) {
        this.hiredTas = hiredTas;
    }

    /**
     * Gets quarter.
     *
     * @return the quarter
     */
    public Integer getQuarter() {
        return quarter;
    }

    /**
     * Sets quarter.
     *
     * @param quarter the quarter
     */
    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }
}
