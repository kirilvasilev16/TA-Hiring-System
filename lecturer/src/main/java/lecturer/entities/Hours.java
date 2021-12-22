package lecturer.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hours {
    private String studentId;
    @JsonProperty("hours")
    private float amountOfHours;
    private String courseId;

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

    public String getStudentId() {
        return studentId;
    }

    public float getAmountOfHours() {
        return amountOfHours;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setAmountOfHours(float amountOfHours) {
        this.amountOfHours = amountOfHours;
    }
}
