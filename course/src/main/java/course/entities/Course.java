package course.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    private String courseName;
    private Integer courseSize;
    @OneToMany
    private Set<Lecturer> lecturerSet;
    private Integer requiredTAs;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startingDate;
    @OneToMany
    private List<Student> candidateTAs;
    @OneToMany
    private List<Management> hiredTAs;

    public Course(String courseName, Set<Lecturer> lecturerSet, Date startingDate) {
        this.courseName = courseName;
        this.lecturerSet = lecturerSet;
        this.startingDate = startingDate;
        courseSize = 0;
        requiredTAs = 0;
        candidateTAs = new ArrayList<>();
        hiredTAs = new ArrayList<>();
    }

    public Course() {

    }

    public String getCourseName() {
        return courseName;
    }
}
