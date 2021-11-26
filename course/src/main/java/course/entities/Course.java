package course.entities;

import javax.persistence.*;
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
    @OneToMany(cascade = {CascadeType.PERSIST})
    private Set<Lecturer> lecturerSet;
    private Integer requiredTAs;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startingDate;
    @OneToMany(cascade = {CascadeType.PERSIST})
    private List<Student> candidateTAs;
    @OneToMany(cascade = {CascadeType.PERSIST})
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
}
