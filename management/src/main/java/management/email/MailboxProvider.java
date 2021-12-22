package management.email;

public class MailboxProvider implements EmailSender {

    /**
     * Send contract via email.
     *
     * @param email email to send contract to
     * @param courseId the id of course
     * @param studentId the id of student
     * @param hours the hours in the contract
     */
    @Override
    public void sendEmail(String email, String courseId, String studentId, float hours) {
        String contract = "Contract:\n"
                + "Student: " + studentId + "\n"
                + "Course: " + courseId + "\n"
                + "Hours in contract: " + hours + "\n";
        System.out.println(contract);
    }
}
