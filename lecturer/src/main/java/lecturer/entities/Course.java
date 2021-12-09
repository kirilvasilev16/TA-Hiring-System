package lecturer.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Course {
    @Id
    private String id;
    private int size;
    @ManyToMany
    private List<Student> candidateTas;

    public Course() {
    }

    public Course(String id, List<Student> candidateTas) {
        this.id = id;
        this.candidateTas = candidateTas;
    }

    public String getId() {
        return id;
    }

    public List<Student> getCandidateTas() {
        return candidateTas;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCandidateTas(List<Student> candidateTas) {
        this.candidateTas = candidateTas;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
