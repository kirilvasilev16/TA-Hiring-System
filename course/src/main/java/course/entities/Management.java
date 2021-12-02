package course.entities;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.Table;


public class Management {
    String name;
    Float rating;

    public Management(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
