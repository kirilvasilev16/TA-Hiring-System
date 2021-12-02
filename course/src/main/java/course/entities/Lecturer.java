package course.entities;


public class Lecturer {

    private String lecturerId;

    /**
     * Lecturer Constructor.
     *
     * @param lecturerId String lecturer netID
     */
    public Lecturer(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    /**
     * Getter for LecturerID.
     *
     * @return String lecturer netID
     */
    public String getLecturerId() {
        return lecturerId;
    }

    /**
     * Setter for lecturerID.
     *
     * @param lecturerId String lecturer nedID
     */
    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }
}
