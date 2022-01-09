package course.controllers;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * The type Course creation body.
 */
public class CourseCreationBody {
    private String courseId;
    private String name;
    private LocalDateTime startingDate;
    private Integer courseSize;
    private Set<String> lecturerSet;
    private Integer quarter;

    /**
     * CourseCreationBody Constructor, object used by admin to pass course information.
     *
     * @param courseId     String courseID
     * @param name         String course name
     * @param startingDate LocalDateTime course start date
     * @param lecturerSet  Set of strings where strings are lecturerIDs
     * @param courseSize   Integer course student size
     * @param quarter      Integer quarter
     */
    public CourseCreationBody(String courseId, String name, LocalDateTime startingDate,
                              Set<String> lecturerSet, Integer courseSize, Integer quarter) {
        this.courseId = courseId;
        this.name = name;
        this.startingDate = startingDate;
        this.lecturerSet = lecturerSet;
        this.courseSize = courseSize;
        this.quarter = quarter;
    }

    /**
     * Getter for courseID.
     *
     * @return String courseID
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Setter for courseID.
     *
     * @param courseId String new courseID
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
        return name;
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
     * @return LocalDateTime object
     */
    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    /**
     * Setter for course starting date.
     *
     * @param startingDate LocalDateTime object new starting date
     */
    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
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
     * Getter for course student size.
     *
     * @return Integer number of students
     */
    public Integer getCourseSize() {
        return courseSize;
    }

    /**
     * Setter for course student size.
     *
     * @param courseSize Integer new number of students
     */
    public void setCourseSize(Integer courseSize) {
        this.courseSize = courseSize;
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
