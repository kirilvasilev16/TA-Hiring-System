package management.services;

import java.util.List;
import javax.transaction.Transactional;
import management.entities.Hours;
import management.entities.Management;
import management.exceptions.InvalidApprovedHoursException;
import management.exceptions.InvalidContractHoursException;
import management.exceptions.InvalidIdException;
import management.exceptions.InvalidRatingException;
import management.repositories.ManagementRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {

    private final transient ManagementRepository managementRepository;

    public ManagementService(ManagementRepository managementRepository) {
        this.managementRepository = managementRepository;
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
    @Transactional(rollbackOn = {InvalidIdException.class, InvalidContractHoursException.class})
    public void declareHours(List<Hours> hoursList) {
        for (Hours hourObject : hoursList) {
            float hours = hourObject.getAmountOfHours();

            if (hours < 0) {
                throw new InvalidContractHoursException("You cannot declare negative "
                        + "amount of hours!");
            }

            Management management = getOne(hourObject.getCourseId(), hourObject.getStudentId());

            if (management.getDeclaredHours() + management.getApprovedHours() + hours
                    > management.getAmountOfHours()) {
                throw new InvalidContractHoursException("You cannot declare more hours "
                        + "the ones in your contract!");
            }

            management.setDeclaredHours(management.getDeclaredHours() + hours);
            managementRepository.updateDeclaredHours(management.getId(),
                    management.getDeclaredHours());
        }
    }

    /**
     * Approved declared hours.
     * Rollbacks all updates on exception.
     *
     * @param hoursList arraylist with declarations
     */
    @Transactional(rollbackOn = {InvalidIdException.class, InvalidApprovedHoursException.class})
    public void approveHours(List<Hours> hoursList) {
        for (Hours hourObject : hoursList) {
            float hours = hourObject.getAmountOfHours();

            if (hours < 0) {
                throw new InvalidApprovedHoursException("You cannot approve negative "
                        + "amount of hours!");
            }

            Management management = getOne(hourObject.getCourseId(), hourObject.getStudentId());

            if (management.getDeclaredHours() < hours) {
                throw new InvalidApprovedHoursException("You cannot approve more hours "
                        + "than the declared ones!");
            }

            management.setDeclaredHours(management.getDeclaredHours() - hours);
            management.setApprovedHours(management.getApprovedHours() + hours);
            managementRepository.updateApprovedHours(management.getId(),
                    management.getDeclaredHours(), management.getApprovedHours());
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

        String contract = "Contract:\n"
                + "Hours in contract: " + management.getAmountOfHours() + "\n";
        System.out.println(contract);
    }

    /**
     * Gets the average rating of a TA.
     *
     * @param studentId id of student
     * @return the management object
     */
    public float getAverageRating(String studentId) {
        if (managementRepository.getTaRecords(studentId) == 0) {
            throw new InvalidIdException("StudentId not found");
        }
        return managementRepository.getAverageTaRating(studentId);
    }
}
