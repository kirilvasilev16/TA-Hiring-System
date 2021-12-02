package course.entities;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;


public class Student {

    private String studentID;

    /**
     * Student Constructor
     * @param studentID String student netID
     */
    public Student(String studentID) {
        this.studentID = studentID;
    }

    /**
     * Getter for student netID
     * @return string
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Setter for student netID
     * @param studentID String student netID
     */
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
