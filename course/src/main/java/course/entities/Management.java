package course.entities;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "management")
public class Management {
    @Id
    String name;

    public Management(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
