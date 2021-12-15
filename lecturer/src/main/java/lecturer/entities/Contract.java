package lecturer.entities;

public class Contract {
    private String studentId;
    private int hours;
    private String courseId;

    public Contract(String courseId, String studentId, int hours) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.hours = hours;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getHours() {
        return hours;
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

    public void setHours(int hours) {
        this.hours = hours;
    }
}
