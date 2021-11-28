package course.entities;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
    @Id
    private String name;

    public Student(String name) {
        this.name = name;
    }

}
