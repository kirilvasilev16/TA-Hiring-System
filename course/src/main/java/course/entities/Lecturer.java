package course.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class Lecturer {
    private String name;

    public Lecturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
