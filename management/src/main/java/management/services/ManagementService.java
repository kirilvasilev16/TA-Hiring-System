package management.services;

import java.util.List;
import javax.transaction.Transactional;
import management.email.EmailSender;
import management.entities.Hours;
import management.entities.Management;
import management.exceptions.InvalidHoursException;
import management.exceptions.InvalidIdException;
import management.exceptions.InvalidRatingException;
import management.repositories.ManagementRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {

    private final transient ManagementRepository managementRepository;
    private final transient EmailSender emailSender;

    public ManagementService(ManagementRepository managementRepository, EmailSender emailSender) {
        this.managementRepository = managementRepository;
        this.emailSender = emailSender;
    }

    /**
     * Find all managements stored in the database.
     *
     * @return the list
     */
    public List<Management> findAll() {
        return managementRepository.findAll();
    }

    /**
     * Get the specified management object.
     *
     * @param courseId the course id
     * @param studentId the student id
     * @return the management object
     */
    public Management getOne(String courseId, String studentId) {
        Management management = managementRepository
                .getManagementByCourseAndStudent(courseId, studentId);
        if (management == null) {
            throw new InvalidIdException("Management object not found.");
        }
        return management;
    }

    /**
     * Create a new Management object.
     *
     * @param courseId id of course
     * @param studentId id of student
     * @param amountOfHours amount of contract hours
     * @return the management object
     */
    public Management createManagement(String courseId, String studentId, float amountOfHours) {
        Management management = new Management(courseId, studentId, amountOfHours);

        return managementRepository.save(management);
    }

    /**
     * Increase declared hours.
     * Rollbacks all updates on exception.
     *
     * @param hoursList arraylist with declarations
     */
    @Transactional(rollbackOn = {InvalidIdException.class, InvalidHoursException.class})
    public void declareHours(List<Hours> hoursList) {
        for (Hours hourObject : hoursList) {
            float hours = hourObject.getAmountOfHours();

            if (hours < 0) {
                throw new InvalidHoursException("You cannot declare negative "
                        + "amount of hours!");
            }

            Management management = getOne(hourObject.getCourseId(), hourObject.getStudentId());

            if (management.getDeclaredHours() + management.getApprovedHours() + hours
                    > management.getAmountOfHours()) {
                throw new InvalidHoursException("You cannot declare more hours "
                        + "the ones in your contract!");
            }

            management.setDeclaredHours(management.getDeclaredHours() + hours);
            managementRepository.updateDeclaredHours(management.getId(),
                    management.getDeclaredHours());
        }
    }

    /**
     * Approve declared hours.
     * Rollbacks all updates on exception.
     *
     * @param hoursList arraylist with declarations
     */
    @Transactional(rollbackOn = {InvalidIdException.class, InvalidHoursException.class})
    public void approveHours(List<Hours> hoursList) {
        for (Hours hourObject : hoursList) {
            float hours = hourObject.getAmountOfHours();

            if (hours < 0) {
                throw new InvalidHoursException("You cannot approve negative "
                        + "amount of hours!");
            }

            Management management = getOne(hourObject.getCourseId(), hourObject.getStudentId());

            if (management.getDeclaredHours() < hours) {
                throw new InvalidHoursException("You cannot approve more hours "
                        + "than the declared ones!");
            }

            management.setDeclaredHours(management.getDeclaredHours() - hours);
            management.setApprovedHours(management.getApprovedHours() + hours);
            managementRepository.updateApprovedHours(management.getId(),
                    management.getDeclaredHours(), management.getApprovedHours());
        }
    }

    /**
     * Disapprove declared hours.
     * Rollbacks all updates on exception.
     *
     * @param hoursList arraylist with declarations
     */
    @Transactional(rollbackOn = {InvalidIdException.class, InvalidHoursException.class})
    public void disapproveHours(List<Hours> hoursList) {
        for (Hours hourObject : hoursList) {
            float hours = hourObject.getAmountOfHours();

            if (hours < 0) {
                throw new InvalidHoursException("You cannot disapprove negative "
                        + "amount of hours!");
            }

            Management management = getOne(hourObject.getCourseId(), hourObject.getStudentId());

            if (management.getDeclaredHours() < hours) {
                throw new InvalidHoursException("You cannot disapprove more hours "
                        + "than the declared ones!");
            }

            management.setDeclaredHours(management.getDeclaredHours() - hours);
            managementRepository.updateDeclaredHours(management.getId(),
                    management.getDeclaredHours());
        }
    }

    /**
     * Rate a student.
     *
     * @param courseId the id of course
     * @param studentId the id of student
     * @param rating new rating
     */
    public void rateStudent(String courseId, String studentId, float rating) {
        if (rating < 0 || rating > 10) {
            throw new InvalidRatingException("You cannot rate a student with"
                    + " less than 0 or more than 10!");
        }

        Management management = getOne(courseId, studentId);

        management.setRating(rating);
        managementRepository.updateRating(management.getId(), rating);
    }

    /**
     * Send the contract using the management id and email to student.
     *
     * @param courseId the id of course
     * @param studentId the id of student
     * @param email the email
     */
    public void sendContract(String courseId, String studentId, String email) {
        Management management = getOne(courseId, studentId);

        emailSender.sendEmail(email, courseId, studentId, management.getAmountOfHours());
    }

    /**
     * Gets the average rating of a TA.
     *
     * @param studentId id of student
     * @return the management object
     */
    public float getAverageRating(String studentId) {
        if (managementRepository.getTaRecords(studentId) == 0) {
            return -1;
        }

        return managementRepository.getAverageTaRating(studentId);
    }
}
