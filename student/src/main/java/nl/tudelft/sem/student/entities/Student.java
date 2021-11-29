package nl.tudelft.sem.student.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity(name = "Student")
public class Student {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

//    @Column(name = "passedCourses")
//    private Map<Long, Float> passedCourses;
//
//    @Column(name = "candidateCourses")
//    private List<Long> candidateCourses;
//
//    @Column(name = "taCourses")
//    private List<Long> taCourses;

    public Student(long id, String name) {
        this.id = id;
        this.name = name;
//        this.passedCourses = new HashMap<>();
//        this.candidateCourses = new ArrayList<>();
//        this.taCourses = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
