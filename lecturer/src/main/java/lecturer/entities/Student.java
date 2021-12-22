package lecturer.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

public class Student {
    private String id;
    private double averageRating;

    public Student(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
