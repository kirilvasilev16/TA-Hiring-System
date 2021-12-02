package course.entities;


public class Lecturer {

    private String lecturerID;

    /**
     * Lecturer Constructor
     * @param lecturerID String lecturer netID
     */
    public Lecturer(String lecturerID) {
        this.lecturerID = lecturerID;
    }

    /**
     * Getter for LecturerID
     * @return String lecturer netID
     */
    public String getLecturerID() {
        return lecturerID;
    }

    /**
     * Setter for lecturerID
     * @param lecturerID String lecturer nedID
     */
    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }
}
