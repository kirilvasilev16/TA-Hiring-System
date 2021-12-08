package course.services;

import course.entities.Course;
import course.exceptions.InvalidCandidateException;
import course.exceptions.InvalidHiringException;
import java.util.Set;

public class StudentService {

    private static final CommunicationService communicationService = new CommunicationService();

    /**
     * Getter for candidate TAs.
     *
     * @param course Course Object
     * @return Set of strings where strings are studentIDs
     */
    public static Set<String> getCandidates(Course course) {
        return course.getCandidateTas();
    }

    /**
     * Add set of candidate TAs, admin privilege.
     *
     * @param course   Course Object
     * @param students Set of strings where strings are studentIDs
     */
    public static void addCandidateSet(Course course, Set<String> students) {
        course.getCandidateTas().addAll(students);
    }

    /**
     * Add student as candidate TA.
     *
     * @param course    Course Object
     * @param studentId String studentId
     * @throws InvalidCandidateException if Student already hired as TA
     */
    public static void addCandidate(Course course, String studentId)
            throws InvalidCandidateException {
        if (containsTa(course, studentId)) {
            throw new InvalidCandidateException("Student already hired as TA");
        }
        course.getCandidateTas().add(studentId);
    }

    /**
     * Remove student from candidate list.
     *
     * @param course    Course Object
     * @param studentId String studentId
     * @return true if removed, false otherwise
     * @throws InvalidCandidateException if Student not a candidate TA
     */
    public static boolean removeCandidate(Course course, String studentId)
            throws InvalidCandidateException {
        if (!containsCandidate(course, studentId)) {
            throw new InvalidCandidateException("Student not a candidate TA");
        }
        return course.getCandidateTas().remove(studentId);
    }

    /**
     * Check if student is in the candidate list.
     *
     * @param course    Course Object
     * @param studentId String studentId
     * @return true if student is candidate, false otherwise
     */
    public static boolean containsCandidate(Course course, String studentId) {
        return course.getCandidateTas().contains(studentId);
    }


    //TODO: getTARecommendation

    /**
     * Hire candidate TA to be TA.
     *
     * @param course    Course Object
     * @param studentId String studentId
     * @param hours     float for contract hours
     * @return true if hired, false otherwise
     * @throws InvalidHiringException if student already hired or not in course
     */
    public static boolean hireTa(Course course, String studentId, float hours)
            throws InvalidHiringException {

        //TODO: who checks hours is valid?

        if (course.getCandidateTas().remove(studentId)) {
            course.getHiredTas().add(studentId);
            //TODO: save management object?
            communicationService.createManagement(course.getCourseId(), studentId, hours);
            return true;
        } else {
            if (containsTa(course, studentId)) {
                throw new InvalidHiringException("Student already hired");
            } else {
                throw new InvalidHiringException("Student not in course");
            }
        }
    }

    /**
     * Getter for hired TAs.
     *
     * @param course Course Object
     * @return Set of strings where strings are studentIDs
     */
    public static Set<String> getTaSet(Course course) {
        return course.getHiredTas();
    }


    /**
     * Add set of students as TAs, admin privilege.
     *
     * @param course   Course Object
     * @param students Set of Strings where strings are studentIDs
     */
    public static void addTaSet(Course course, Set<String> students) {
        course.getHiredTas().addAll(students);
    }


    /**
     * Remove student from TA set.
     *
     * @param course    Course Object
     * @param studentId String studentId
     * @return true if removed, false otherwise
     */
    public static boolean removeTa(Course course, String studentId) {
        return course.getHiredTas().remove(studentId);
    }

    /**
     * Check if student is a TA.
     *
     * @param course    Course Object
     * @param studentId String studentId
     * @return true is student is a TA, false otherwise
     */
    public static boolean containsTa(Course course, String studentId) {
        return course.getHiredTas().contains(studentId);
    }

    //TODO: getAvgWorkedHOurs

    /**
     * Check if enough TA have been hired.
     *
     * @param course Course Object
     * @return true if enough, false otherwise
     */
    public static boolean enoughTas(Course course) {
        return course.getHiredTas().size() >= course.getRequiredTas();
    }

    /**
     * Get number of TAs hired.
     *
     * @param course Course object
     * @return int
     */
    public static int hiredTas(Course course) {
        return course.getHiredTas().size();
    }
}
