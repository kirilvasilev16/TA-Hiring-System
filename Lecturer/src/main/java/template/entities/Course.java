package template.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Course {
    @Id
    private Long id;
    @ManyToMany
    private List<Student> candidateTas;

    public Long getId() {
        return id;
    }

    public List<Student> getCandidateTas() {
        return candidateTas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCandidateTas(List<Student> candidateTas) {
        this.candidateTas = candidateTas;
    }
}
