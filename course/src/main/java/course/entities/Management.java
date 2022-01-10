package course.entities;


public class Management {
    String name;
    Float rating;
    String managementId;

    /**
     * Management Constructor.
     */
    public Management() {
    }

    /**
     * Getter for management ID.
     *
     * @return String
     */
    public String getManagementId() {
        return managementId;
    }

    /**
     * Setter for management ID.
     *
     * @param managementId String management ID
     */
    public void setManagementId(String managementId) {
        this.managementId = managementId;
    }

    /**
     * Getter for management rating.
     *
     * @return float rating
     */
    public Float getRating() {
        return rating;
    }

    /**
     * Setter for management rating.
     *
     * @param rating float
     */
    public void setRating(Float rating) {
        this.rating = rating;
    }
}
