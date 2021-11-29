package management.entities;

import static javax.persistence.GenerationType.SEQUENCE;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import management.serializers.ManagementSerializer;

@Entity(name = "Management")
@JsonSerialize(using = ManagementSerializer.class)
public class Management {

    @Id
    @SequenceGenerator(
            name = "management_sequence",
            sequenceName = "management_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "management_sequence"
    )
    @Column(name = "id", updatable = false)
    private long id;
    @Column
    private String courseId;
    @Column
    private String studentId;
    @Column
    private float amountOfHours;
    @Column
    private float approvedHours;
    @Column
    private float declaredHours;
    @Column
    private float rating;

    /**
     * Default constructor required by Spring.
     */
    public Management() {}

    /**
     * Constructor for management.
     *
     * @param courseId id of course
     * @param studentId id of student
     * @param amountOfHours contract hours
     */
    public Management(String courseId, String studentId, float amountOfHours) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.amountOfHours = amountOfHours;
        this.approvedHours = 0;
        this.declaredHours = 0;
        this.rating = 0;
    }

    /**
     * Getter for id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
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
     * Getter for approved hours.
     *
     * @return approved hours
     */
    public float getApprovedHours() {
        return approvedHours;
    }

    /**
     * Setter for approved hours.
     *
     * @param approvedHours approved hours
     */
    public void setApprovedHours(float approvedHours) {
        this.approvedHours = approvedHours;
    }

    /**
     * Getter for declared hours.
     *
     * @return declared hours
     */
    public float getDeclaredHours() {
        return declaredHours;
    }

    /**
     * Setter for declared hours.
     *
     * @param declaredHours declared hours
     */
    public void setDeclaredHours(float declaredHours) {
        this.declaredHours = declaredHours;
    }

    /**
     * Getter for rating.
     *
     * @return the rating
     */
    public float getRating() {
        return rating;
    }

    /**
     * Setter for rating.
     *
     * @param rating the new rating
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Equals method for Management.
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
        Management that = (Management) o;
        return id == that.id
                && courseId == that.courseId
                && studentId == that.studentId
                && Float.compare(that.amountOfHours, amountOfHours) == 0
                && Float.compare(that.approvedHours, approvedHours) == 0
                && Float.compare(that.declaredHours, declaredHours) == 0
                && Float.compare(that.rating, rating) == 0;
    }

    /**
     * Hash management object.
     *
     * @return hash of object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, studentId, amountOfHours,
                approvedHours, declaredHours, rating);
    }
}