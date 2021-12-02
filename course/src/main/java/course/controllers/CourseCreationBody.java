package course.controllers;

import course.entities.Lecturer;

import java.util.Date;
import java.util.Set;

public class CourseCreationBody {
    private String courseID;
    private String name;
    private Date startingDate;
    private Integer courseSize;
    private Set<String> lecturerSet;

    /**
     * CourseCreationBody Constructor, object used by admin to pass course information
     * @param courseID String courseID
     * @param name String course name
     * @param startingDate Date course start date
     * @param lecturerSet Set of strings where strings are lecturerIDs
     * @param courseSize Integer course student size
     */
    public CourseCreationBody(String courseID, String name, Date startingDate, Set<String> lecturerSet, Integer courseSize) {
        this.courseID = courseID;
        this.name = name;
        this.startingDate = startingDate;
        this.lecturerSet = lecturerSet;
        this.courseSize = courseSize;
    }

    /**
     * Getter for courseID
     * @return String courseID
     */
    public String getCourseID() {
        return courseID;
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
        return name;
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
        return startingDate;
    }

    /**
     * Setter for course starting date
     * @param startingDate Date object new starting date
     */
    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
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
     * Getter for course student size
     * @return Integer number of students
     */
    public Integer getCourseSize() {
        return courseSize;
    }

    /**
     * Setter for course student size
     * @param courseSize Integer new number of students
     */
    public void setCourseSize(Integer courseSize) {
        this.courseSize = courseSize;
    }
}
