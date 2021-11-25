package management.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "management")
public class Management {

    @Id
    @SequenceGenerator(
            name = "management_sequence",
            sequenceName = "management_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "management_sequence"
    )
    @Column(name = "id", updatable = false)
    private long id;

    private String name;

    /**
     * Default constructor required by Spring.
     */
    public Management() {}

    public Management(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}