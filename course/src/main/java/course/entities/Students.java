package course.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Students {
    private Set<String> candidates;
    private Set<String> tas;
    private int requiredTAs;

    /**
     * Constructor for Students object, initializes requiredTAs amount to 1:20 TA student ratio
     * @param courseSize integer representing number of student in course
     */
    public Students(int courseSize) {
        this.requiredTAs = courseSize / 5;
        this.candidates = new HashSet<>();
        this.tas = new HashSet<>();
    }

    /**
     * Getter for candidate TAs
     * @return Set of strings where strings are studentIDs
     */
    public Set<String> getCandidateSet(){
        return this.candidates;
    }

    /**
     * Add set of candidate TAs, admin privilege
     * @param students Set of strings where strings are studentIDs
     */
    public void addCandidateSet(Set<String> students){
        this.candidates.addAll(students);
    }

    /**
     * Add student as candidate TA
     * @param studentID String studentID
     */
    public void addCandidate(String studentID){
        if(containsTA(studentID)) return; //TODO: throw candidate already hired
        this.candidates.add(studentID);
    }

    /**
     * Remove student from candidate list
     * @param studentID String studentID
     * @return true if removed, false otherwise
     */
    public boolean removeCandidate(String studentID){
        if(!containsCandidate(studentID) || containsTA(studentID)) return false; //TODO: throw not candidate
        return this.candidates.remove(studentID);
    }

    /**
     * Check if student is in the candidate list
     * @param studentID String studentID
     * @return true if student is candidate, false otherwise
     */
    public boolean containsCandidate(String studentID){
        return this.candidates.contains(studentID);
    }


    //TODO: getTARecommendation

    /**
     * Hire candidate TA to be TA
     * @param studentID String studentID
     * @return true if hired, false otherwise
     */
    public boolean hireTA(String studentID){

        if(removeCandidate(studentID)){
            addTA(studentID);
            //TODO: access management microservice to create management
            return true;
        }else{
            if(containsTA(studentID)) return false; //TODO: throws student already hired
            else return false; //TODO: throw student not in course exception
        }
    }

    /**
     * Getter for hired TAs
     * @return Set of strings where strings are studentIDs
     */
    public Set<String> getTASet(){
        return this.tas;
    }

    /**
     * Add set of students as TAs, admin privilege
     * @param students Set of Strings where strings are studentIDs
     */
    public void addTASet(Set<String> students){
        this.tas.addAll(students);
    }

    /**
     * Add student as TA
     * @param studentID String studentID
     */
    private void addTA(String studentID){
        this.tas.add(studentID);
    }

    /**
     * Remove student from TA set
     * @param studentID String studentID
     * @return true if removed, false otherwise
     */
    public boolean removeTA(String studentID){
        return this.tas.remove(studentID);
    }

    /**
     * Check if student is a TA
     * @param studentID String studentID
     * @return true is student is a TA, false otherwise
     */
    public boolean containsTA(String studentID){
        return this.tas.contains(studentID);
    }

    //TODO: getAvgWorkedHOurs

    /**
     * Check if enough TA have been hired
     * @return true if enough, false otherwise
     */
    public boolean enoughTAs(){
        return this.tas.size() >= this.requiredTAs;
    }

    /**
     * Getter for requiredTAs
     * @return integer value for requiredTAs
     */
    public int getRequiredTAs(){
        return this.requiredTAs;
    }

    /**
     * Setter for requiredTAs, requiredTAs amounts to 1:20 TA student ratio
     * @param courseSize integer representing number of student in course
     */
    public void setRequiredTAs(int courseSize){
        this.requiredTAs = courseSize / 5;
    }

    /**
     * Equals method for Students object
     * @param o Object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Students students = (Students) o;
        return requiredTAs == students.requiredTAs && candidates.equals(students.candidates) && tas.equals(students.tas);
    }

}
