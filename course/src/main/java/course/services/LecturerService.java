package course.services;

import course.entities.Course;
import java.util.Set;


public class LecturerService {

    /**
     * Getter for set of lecturers
     * @param course Course Object
     * @return Set of strings where strings are lecturerIDs
     */
    public static Set<String> getLecturerSet(Course course){
        return course.getLecturerSet();
    }

    /**
     * Add set of lecturers to the course, admin privileges
     * @param course Course Object
     * @param lecturerIDs Set of strings where stings are lecturerIDs
     */
    public static void addLecturerSet(Course course, Set<String> lecturerIDs){
        course.getLecturerSet().addAll(lecturerIDs);
    }

    /**
     * Add lecturer to the source
     * @param course Course Object
     * @param lecturerID String lecturerID
     */
    public static void addLecturer(Course course, String lecturerID){
        course.getLecturerSet().add(lecturerID);
    }

    /**
     * Checks if a lecturer is a lecturer of the course
     * @param course Course Object
     * @param lecturerID String lecturerID
     * @return true if lecturer part of course, false otherwise
     */
    public static boolean containsLecturer(Course course, String lecturerID){
        return course.getLecturerSet().contains(lecturerID);
    }

}
