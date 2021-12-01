package course.entities;

import java.util.HashSet;
import java.util.Set;

public class Lecturers {
    private Set<String> lecturerList;

    public Lecturers() {
        lecturerList = new HashSet<>();
    }

    Set<String> getLecturerSet(){
        return lecturerList;
    }

    void addLecturerSet(Set<String> lecturerIDs){
        this.lecturerList.addAll(lecturerIDs);
    }

    void addLecturer(String lecturerID){
        this.lecturerList.add(lecturerID);
    }

    boolean isLecturer(String lecturerID){
        return this.lecturerList.contains(lecturerID);
    }


}
