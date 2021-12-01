package course.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Lecturers {
    private Set<String> lecturerList;

    /**
     * Constructor for Lecturers object.
     */
    public Lecturers() {
        lecturerList = new HashSet<>();
    }

    /**
     * Getter for set of lecturers
     * @return Set of strings where strings are lecturerIDs
     */
    public Set<String> getLecturerSet(){
        return lecturerList;
    }

    /**
     * Add set of lecturers to the course, admin privileges
     * @param lecturerIDs Set of strings where stings are lecturerIDs
     */
    public void addLecturerSet(Set<String> lecturerIDs){
        this.lecturerList.addAll(lecturerIDs);
    }

    /**
     * Add lecturer to the source
     * @param lecturerID String lecturerID
     */
    public void addLecturer(String lecturerID){
        this.lecturerList.add(lecturerID);
    }

    /**
     * Checks if a lecturer is a lecturer of the course
     * @param lecturerID String lecturerID
     * @return true if lecturer part of course, false otherwise
     */
    public boolean containsLecturer(String lecturerID){
        return this.lecturerList.contains(lecturerID);
    }

    /**
     * Equals method for Lecturers object
     * @param o Object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecturers lecturers = (Lecturers) o;
        return lecturerList.equals(lecturers.lecturerList);
    }

}
