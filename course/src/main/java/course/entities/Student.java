package course.entities;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
    @Id
    String name;

    public Student() {
    }

}
