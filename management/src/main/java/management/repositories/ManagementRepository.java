package management.repositories;

import javax.transaction.Transactional;
import management.entities.Management;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagementRepository extends JpaRepository<Management, Long> {

    /**
     * Query Management object based on courseId and studentId.
     *
     * @param courseId id of course
     * @param studentId id of student
     * @return the management object
     */
    @Transactional
    @Query(value = "SELECT m FROM Management m WHERE m.courseId = ?1 AND m.studentId = ?2")
    Management getManagement(String courseId, String studentId);

    /**
     * Update declared hours.
     *
     * @param id the id of the management object
     * @param declaredHours the new declared hours
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Management m SET m.declaredHours = ?2 WHERE m.id = ?1")
    void updateDeclaredHours(long id, float declaredHours);

    /**
     * Update approved hours.
     *
     * @param id the id of the management object
     * @param declaredHours the new declared hours
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Management m SET m.declaredHours = ?2, "
            + "m.approvedHours = ?3 WHERE m.id = ?1")
    void updateApprovedHours(long id, float declaredHours, float approvedHours);


    /**
     * Update student rating.
     *
     * @param id the id of the management object
     * @param rating new rating
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Management m SET m.rating = ?2 WHERE m.id = ?1")
    void updateRating(long id, float rating);
}
