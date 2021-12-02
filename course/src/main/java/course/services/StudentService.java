package course.services;

import course.controllers.exceptions.InvalidCandidateException;
import course.controllers.exceptions.InvalidHiringException;
import course.entities.Course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.swing.plaf.IconUIResource;
import java.util.HashSet;
import java.util.Set;

public class StudentService {


    /**
     * Getter for candidate TAs
     * @return Set of strings where strings are studentIDs
     */
    public static Set<String> getCandidates(Course course){
        return course.getCandidateTAs();
    }

    /**
     * Add set of candidate TAs, admin privilege
     * @param students Set of strings where strings are studentIDs
     */
    public static void addCandidateSet(Course course, Set<String> students){
        course.getCandidateTAs().addAll(students);
    }

    /**
     * Add student as candidate TA
     * @param studentID String studentID
     */
    public static void addCandidate(Course course, String studentID){
        if(containsTA(course, studentID)) throw new InvalidCandidateException("Student already hired as TA");
        course.getCandidateTAs().add(studentID);
    }

    /**
     * Remove student from candidate list
     * @param studentID String studentID
     * @return true if removed, false otherwise
     */
    public static boolean removeCandidate(Course course, String studentID){
        if(!containsCandidate(course, studentID)) throw new InvalidCandidateException("Student not a candidate TA");
        return course.getCandidateTAs().remove(studentID);
    }

    /**
     * Check if student is in the candidate list
     * @param studentID String studentID
     * @return true if student is candidate, false otherwise
     */
    public static boolean containsCandidate(Course course, String studentID){
        return course.getCandidateTAs().contains(studentID);
    }


    //TODO: getTARecommendation

    /**
     * Hire candidate TA to be TA
     * @param studentID String studentID
     * @return true if hired, false otherwise
     */
    public static boolean hireTA(Course course, String studentID){

        if(course.getCandidateTAs().remove(studentID)){
            course.getHiredTAs().add(studentID);
            //TODO: access management microservice to create management
            return true;
        }else{
            if(containsTA(course, studentID)) throw new InvalidHiringException("Student already hired");
            else throw new InvalidHiringException("Student not in course");
        }
    }

    /**
     * Getter for hired TAs
     * @return Set of strings where strings are studentIDs
     */
    public static Set<String> getTASet(Course course){
        return course.getHiredTAs();
    }

    /**
     * Add set of students as TAs, admin privilege
     * @param students Set of Strings where strings are studentIDs
     */
    public static void addTASet(Course course, Set<String> students){
        course.getHiredTAs().addAll(students);
    }


    /**
     * Remove student from TA set
     * @param studentID String studentID
     * @return true if removed, false otherwise
     */
    public static boolean removeTA(Course course, String studentID){
        return course.getHiredTAs().remove(studentID);
    }

    /**
     * Check if student is a TA
     * @param studentID String studentID
     * @return true is student is a TA, false otherwise
     */
    public static boolean containsTA(Course course, String studentID){
        return course.getHiredTAs().contains(studentID);
    }

    //TODO: getAvgWorkedHOurs

    /**
     * Check if enough TA have been hired
     * @return true if enough, false otherwise
     */
    public static boolean enoughTAs(Course course){
        return course.getHiredTAs().size() >= course.getRequiredTAs();
    }

    public static int hiredTAs(Course course){
        return course.getHiredTAs().size();
    }
}
