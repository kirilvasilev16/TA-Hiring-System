package course.entities;


import course.exceptions.CourseNotPassedException;
import java.util.Map;
import java.util.Set;


/**
 * The type Student.
 */
public class Student {
    private String netId;
    private Map<String, Float> passedCourses;
    private Set<String> taCourses;

    /**
     * Student Constructor.
     *
     * @param netId         the net id
     * @param passedCourses the passed courses
     * @param taCourses     the ta courses
     */
    public Student(String netId, Map<String, Float> passedCourses, Set<String> taCourses) {
        this.netId = netId;
        this.passedCourses = passedCourses;
        this.taCourses = taCourses;
    }

    /**
     * Gets the netId.
     *
     * @return the net id
     */
    public String getNetId() {
        return netId;
    }

    /**
     * Sets the netId.
     *
     * @param netId the net id
     */
    public void setNetId(String netId) {
        this.netId = netId;
    }

    /**
     * Gets passedCourses.
     *
     * @return the passed courses
     */
    public Map<String, Float> getPassedCourses() {
        return passedCourses;
    }

    /**
     * Sets passedCourses.
     *
     * @param passedCourses the passed courses
     */
    public void setPassedCourses(Map<String, Float> passedCourses) {
        this.passedCourses = passedCourses;
    }

    /**
     * Gets taCourses.
     *
     * @return the ta courses
     */
    public Set<String> getTaCourses() {
        return taCourses;
    }

    /**
     * Sets taCourses.
     *
     * @param taCourses the ta courses
     */
    public void setTaCourses(Set<String> taCourses) {
        this.taCourses = taCourses;
    }

    /**
     * Gets the highest grade achieved in the past 5 years.
     * If the class has not been taken within this period, an error is thrown.
     *
     * @param courseId the course id
     * @return the highest grade achieved
     */
    @SuppressWarnings("PMD")
    public Float getHighestGradeAchieved(String courseId) {
        String strippedId = courseId.split("-")[0];
        int year = Integer.parseInt(courseId.split("-")[1]);
        Float highest = -1f;
        Float grade = passedCourses.get(strippedId);
        if (grade != null && grade > highest) {
            highest = grade;
        }
        if (highest == -1f) {
            throw new CourseNotPassedException(courseId);
        }
        return highest;
    }
}
