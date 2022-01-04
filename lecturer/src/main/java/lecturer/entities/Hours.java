package lecturer.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hours {
    private String courseId;
    private String studentId;
    @JsonProperty("hours")
    private float amountOfHours;

    /**
     * Constructor.
     *
     * @param courseId course id
     * @param studentId student id
     * @param hours hours
     */
    public Hours(String courseId, String studentId, float hours) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.amountOfHours = hours;
    }

    /**
     * Empty constructor.
     */
    public Hours() {
    }

    /**
     * Getter for student id.
     *
     * @return student id
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Getter for amount of hours.
     *
     * @return amount of hours
     */
    public float getAmountOfHours() {
        return amountOfHours;
    }

    /**
     * Getter for course id.
     *
     * @return course id
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Setter for course id.
     *
     * @param courseId course id
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Setter for student id.
     *
     * @param studentId student id
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Setter for amount of hours.
     *
     * @param amountOfHours number of hours
     */
    public void setAmountOfHours(float amountOfHours) {
        this.amountOfHours = amountOfHours;
    }
}
