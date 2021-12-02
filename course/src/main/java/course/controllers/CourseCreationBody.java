package course.controllers;

import course.entities.Lecturer;

import java.util.Date;
import java.util.Set;

public class CourseCreationBody {
    private String courseID;
    private String name;
    private Date startingDate;
    private int courseSize;
    private Set<String> lecturerSet;

    public CourseCreationBody(String courseID, String name, Date startingDate, Set<String> lecturerSet, int courseSize) {
        this.courseID = courseID;
        this.name = name;
        this.startingDate = startingDate;
        this.lecturerSet = lecturerSet;
        this.courseSize = courseSize;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Set<String> getLecturerSet() {
        return lecturerSet;
    }

    public void setLecturerSet(Set<String> lecturerSet) {
        this.lecturerSet = lecturerSet;
    }

    public int getCourseSize() {
        return courseSize;
    }

    public void setCourseSize(int courseSize) {
        this.courseSize = courseSize;
    }
}
