package course.controllers;

import course.entities.Lecturer;

import java.util.Date;
import java.util.Set;

public class CourseCreationBody {
    private String courseID;
    private String name;
    private Date startingDate;
    private Set<Lecturer> lecturerSet;

    public CourseCreationBody(String courseID, String name, Date startingDate, Set<Lecturer> lecturerSet) {
        this.courseID = courseID;
        this.name = name;
        this.startingDate = startingDate;
        this.lecturerSet = lecturerSet;
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

    public Set<Lecturer> getLecturerSet() {
        return lecturerSet;
    }

    public void setLecturerSet(Set<Lecturer> lecturerSet) {
        this.lecturerSet = lecturerSet;
    }
}
