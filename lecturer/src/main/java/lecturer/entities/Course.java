package lecturer.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Course {
    @Id
    private String id;
    private int size;
    private int numberOfTa;
    @ElementCollection
    private List<Student> candidateTas;

    public Course() {
    }

    public int getNumberOfTa() {
        return numberOfTa;
    }

    /**
     * Constructor.
     *
     * @param id course id
     * @param candidateTas candidates TAs
     * @param size size of course
     */
    public Course(String id, List<Student> candidateTas, int size) {
        this.id = id;
        this.candidateTas = candidateTas;
        this.size = size;
        this.numberOfTa = size / 20;
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

    public void setNumberOfTa(int numberOfTa) {
        this.numberOfTa = numberOfTa;
    }
}
