package management.services;

import java.util.List;
import management.entities.Management;
import management.exceptions.InvalidApprovedHoursException;
import management.exceptions.InvalidContractHoursException;
import management.exceptions.InvalidIdException;
import management.exceptions.InvalidRatingException;
import management.repositories.ManagementRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {

    private transient String managementNotFound = "Management object not found.";
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
     * @param id the id
     * @return the management object
     */
    public Management getOne(long id) {
        return managementRepository.getOne(id);
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
     *
     * @param id the id of the management object
     * @param hours hours declared
     */
    public void declareHours(long id, long hours) {
        Management management = this.getOne(id);

        if (management == null) {
            throw new InvalidIdException(managementNotFound);
        }

        if (hours < 0) {
            throw new InvalidContractHoursException("You cannot declare negative "
                    + "amount of hours!");
        }

        if (management.getDeclaredHours() + management.getApprovedHours() + hours
                > management.getAmountOfHours()) {
            throw new InvalidContractHoursException("You cannot declare more hours "
                    + "the ones in your contract!");
        }

        management.setDeclaredHours(management.getDeclaredHours() + hours);
        managementRepository.updateDeclaredHours(management.getId(), management.getDeclaredHours());
    }

    /**
     * Approved declared hours.
     *
     * @param id the id of the management object
     * @param hours hours declared
     */
    public void approveHours(long id, long hours) {
        Management management = this.getOne(id);

        if (management == null) {
            throw new InvalidIdException(managementNotFound);
        }

        if (hours < 0) {
            throw new InvalidApprovedHoursException("You cannot approve negative "
                    + "amount of hours!");
        }

        if (management.getDeclaredHours() < hours) {
            throw new InvalidApprovedHoursException("You cannot approve more hours "
                    + "than the declared ones!");
        }

        management.setDeclaredHours(management.getDeclaredHours() - hours);
        management.setApprovedHours(management.getApprovedHours() + hours);
        managementRepository.updateApprovedHours(management.getId(),
                management.getDeclaredHours(), management.getApprovedHours());
    }

    /**
     * Rate a student.
     *
     * @param id the id of the management object
     * @param rating new rating
     */
    public void rateStudent(long id, float rating) {
        Management management = this.getOne(id);

        if (management == null) {
            throw new InvalidIdException(managementNotFound);
        }

        if (rating < 0 || rating > 10) {
            throw new InvalidRatingException("You cannot rate a student with"
                    + " less than 0 or more than 10!");
        }

        management.setRating(rating);
        managementRepository.updateRating(id, rating);
    }

    /**
     * Send the contract using the management id and email to student.
     *
     * @param id the id
     * @param email the email
     */
    public void sendContract(long id, String email) {
        Management management = this.getOne(id);

        if (management == null) {
            throw new InvalidIdException(managementNotFound);
        }

        String contract = "Contract:\n"
                + "Hours in contract: " + management.getAmountOfHours() + "\n";
        System.out.println(contract);
    }
}
