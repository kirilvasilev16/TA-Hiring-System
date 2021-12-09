package management.entities;

import com.google.gson.annotations.JsonAdapter;
import java.util.Objects;
import management.deserializers.HoursDeserialization;

@JsonAdapter(HoursDeserialization.class)
public class Hours {

    private String courseId;
    private String studentId;
    private float amountOfHours;

    /**
     * Hours object used for declaring/approving hours.
     *
     * @param courseId id of course
     * @param studentId id of student
     * @param amountOfHours amount of hours
     */
    public Hours(String courseId, String studentId, float amountOfHours) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.amountOfHours = amountOfHours;
    }

    /**
     * Getter for course id.
     *
     * @return the id
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Setter for course id.
     *
     * @param courseId the id
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Getter for student id.
     *
     * @return the id
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Setter for student id.
     *
     * @param studentId the id
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Getter for contract hours.
     *
     * @return contract hours
     */
    public float getAmountOfHours() {
        return amountOfHours;
    }

    /**
     * Setter for contract hours.
     *
     * @param amountOfHours contract hours
     */
    public void setAmountOfHours(float amountOfHours) {
        this.amountOfHours = amountOfHours;
    }

    /**
     * Equals method for Hours.
     *
     * @param o other object
     * @return true iff the 2 objects are identical
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hours hours = (Hours) o;
        return Float.compare(hours.amountOfHours, amountOfHours) == 0
                && Objects.equals(courseId, hours.courseId)
                && Objects.equals(studentId, hours.studentId);
    }

    /**
     * Hash management object.
     *
     * @return hash of object
     */
    @Override
    public int hashCode() {
        return Objects.hash(courseId, studentId, amountOfHours);
    }
}
