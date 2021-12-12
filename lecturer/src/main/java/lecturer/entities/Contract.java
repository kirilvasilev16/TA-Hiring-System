package lecturer.entities;

public class Contract {
    private String netId;
    private String studentId;
    private int hours;
    private String courseId;

    public Contract(String netId, String courseId, String studentId, int hours) {
        this.netId = netId;
        this.courseId = courseId;
        this.studentId = studentId;
        this.hours = hours;
    }

    public String getNetId() {
        return netId;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getHours() {
        return hours;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
