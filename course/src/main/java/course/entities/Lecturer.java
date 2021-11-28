package course.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lecturer")
public class Lecturer {
    @Id
    private String name;

    public Lecturer(String name) {
        this.name = name;
    }
}
