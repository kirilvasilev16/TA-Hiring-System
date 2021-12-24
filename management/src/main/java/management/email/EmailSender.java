package management.email;

public interface EmailSender {

    /**
     * Send contract via email.
     *
     * @param email email to send contract to
     * @param courseId the id of course
     * @param studentId the id of student
     * @param hours the hours in the contract
     */
    public void sendEmail(String email, String courseId, String studentId, float hours);
}
