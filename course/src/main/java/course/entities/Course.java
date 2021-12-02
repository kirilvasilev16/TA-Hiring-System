package course.entities;

import javax.persistence.*;
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
    private int courseSize;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startingDate;


    private Students students;
    private Lecturers lecturers;

    /**
     * Constructor for Course object
     * @param courseID String courseID
     * @param name String course name
     * @param courseSize int course student size
     * @param startingDate Date course start date
     * @param lecturerSet Set of strings where strings are lecturerIDs
     * @param taSet Set of strings where strings are studentIDs
     * @param candidateSet Set of strings where strings are studentIDs
     */
    public Course(String courseID, String name, int courseSize,Date startingDate, Set<String> lecturerSet, Set<String> taSet, Set<String> candidateSet) {
        this.courseID = courseID;
        this.name = name;
        this.courseSize = courseSize;
        this.startingDate = startingDate;

        this.lecturers = new Lecturers();
        this.students = new Students(courseSize);

        lecturers.addLecturerSet(lecturerSet);
        students.addCandidateSet(candidateSet);
        students.addTASet(taSet);

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
     * Setter for courseID, admin privilege?
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
     * Setter for course name, admin privilege?
     * @param name String new course name
     */
    public void setName(String name) {
        this.name = name;
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
        this.students.setRequiredTAs(courseSize);
    }

    /**
     * Getter for course lecturers
     * @return Lecturers object
     */
    public Lecturers getLecturers() {
        return this.lecturers;
    }


    /**
     * Getter for course starting date
     * @return Date object
     */
    public Date getStartingDate() {
        return this.startingDate;
    }

    /**
     * Setter for course starting date, admin privilege?
     * @param startingDate Date object new starting date
     */
    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    /**
     * Getter for course students
     * @return Students object
     */
    public Students getStudents() {
        return this.students;
    }


}
