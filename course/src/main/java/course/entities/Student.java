package course.entities;



public class Student {

    private String studentId;

    /**
     * Student Constructor.
     *
     * @param studentId String student netID
     */
    public Student(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Getter for student netID.
     *
     * @return string
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Setter for student netID.
     *
     * @param studentId String student netID
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
