package course.services;

import course.entities.Course;
import java.util.Set;


public class LecturerService {

    /**
     * Getter for set of lecturers.
     *
     * @param course Course Object
     * @return Set of strings where strings are lecturerIDs
     */
    public static Set<String> getLecturerSet(Course course) {
        return course.getLecturerSet();
    }

    /**
     * Add set of lecturers to the course, admin privileges.
     *
     * @param course      Course Object
     * @param lecturerIds Set of strings where stings are lecturerIds
     */
    public static void addLecturerSet(Course course, Set<String> lecturerIds) {
        course.getLecturerSet().addAll(lecturerIds);
    }

    /**
     * Add lecturer to the source.
     *
     * @param course     Course Object
     * @param lecturerId String lecturerId
     */
    public static void addLecturer(Course course, String lecturerId) {
        course.getLecturerSet().add(lecturerId);
    }

    /**
     * Checks if a lecturer is a lecturer of the course.
     *
     * @param course     Course Object
     * @param lecturerId String lecturerId
     * @return true if lecturer part of course, false otherwise
     */
    public static boolean containsLecturer(Course course, String lecturerId) {
        return course.getLecturerSet().contains(lecturerId);
    }

}
