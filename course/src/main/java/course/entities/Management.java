package course.entities;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.Table;


public class Management {
    String managementID;

    /**
     * Management Constructor
     */
    public Management(){}

    /**
     * Getter for management ID
     * @return String
     */
    public String getManagementID() {
        return managementID;
    }

    /**
     * Setter for management ID
     * @param managementID String management ID
     */
    public void setManagementID(String managementID) {
        this.managementID = managementID;
    }
}
