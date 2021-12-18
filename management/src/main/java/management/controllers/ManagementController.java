package management.controllers;

import java.util.List;
import javax.websocket.server.PathParam;
import management.entities.Hours;
import management.entities.Management;
import management.services.ManagementService;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("Management")
@RequestMapping("management")
public class ManagementController {
    private final transient ManagementService managementService;

    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    /**
     * Returns all managements stored in the database.
     *
     * @return the list
     */
    @GetMapping("findAll")
    public List<Management> findAll() {
        return managementService.findAll();
    }

    /**
     * Gets the management that has the passed id.
     *
     * @param courseId id of course
     * @param studentId id of student
     * @return the management object
     */
    @GetMapping("get")
    public Management getOne(@PathParam(AnnotationHelper.courseId) String courseId,
                             @PathParam(AnnotationHelper.studentId) String studentId) {
        return managementService.getOne(courseId, studentId);
    }

    /**
     * Gets the rating of a student for a course.
     *
     * @param courseId id of course
     * @param studentId id of student
     * @return the management object
     */
    @GetMapping("getRating")
    public float getRating(@PathParam(AnnotationHelper.courseId) String courseId,
                             @PathParam(AnnotationHelper.studentId) String studentId) {
        return managementService.getOne(courseId, studentId).getRating();
    }

    /**
     * Gets the average rating of a TA.
     *
     * @param studentId id of student
     * @return the management object
     */
    @GetMapping("getAverageRating")
    public float getAverageRating(@PathParam(AnnotationHelper.studentId) String studentId) {
        return managementService.getAverageRating(studentId);
    }

    /**
     * Gets the contract hours of a student for a course.
     *
     * @param courseId id of course
     * @param studentId id of student
     * @return the management object
     */
    @GetMapping("getAmountOfHours")
    public float getAmountOfHours(@PathParam(AnnotationHelper.courseId) String courseId,
                                  @PathParam(AnnotationHelper.studentId) String studentId) {
        return managementService.getOne(courseId, studentId).getAmountOfHours();
    }

    /**
     * Gets the declared hours of a student for a course.
     *
     * @param courseId id of course
     * @param studentId id of student
     * @return the management object
     */
    @GetMapping("getDeclaredHours")
    public float getDeclaredHours(@PathParam(AnnotationHelper.courseId) String courseId,
                           @PathParam(AnnotationHelper.studentId) String studentId) {
        return managementService.getOne(courseId, studentId).getDeclaredHours();
    }

    /**
     * Gets the approved hours of a student for a course.
     *
     * @param courseId id of course
     * @param studentId id of student
     * @return the management object
     */
    @GetMapping("getApprovedHours")
    public float getApprovedHours(@PathParam(AnnotationHelper.courseId) String courseId,
                                  @PathParam(AnnotationHelper.studentId) String studentId) {
        return managementService.getOne(courseId, studentId).getApprovedHours();
    }

    /**
     * Create a new Management object.
     *
     * @param courseId id of course
     * @param studentId id of student
     * @param amountOfHours amount of contract hours
     * @return the management object
     */
    @PostMapping("create")
    public Management create(@PathParam(AnnotationHelper.courseId) String courseId,
                                 @PathParam(AnnotationHelper.studentId) String studentId,
                                 @PathParam("amountOfHours") float amountOfHours) {
        return managementService.createManagement(courseId, studentId, amountOfHours);
    }

    /**
     * Declare working hours for student for a given course.
     *
     * @param hours list of declarations
     */
    @PutMapping("declareHours")
    public void declareHours(@RequestBody List<Hours> hours) {
        managementService.declareHours(hours);
    }

    /**
     * Approve working hours for student for a given course.
     *
     * @param hours list of approvals
     */
    @PutMapping("approveHours")
    public void approveHours(@RequestBody List<Hours> hours) {
        managementService.approveHours(hours);
    }

    /**
     * Rate a student for a given course.
     *
     * @param courseId id of course
     * @param studentId id of student
     * @param rating new rating
     */
    @PutMapping("rate")
    public void rate(@PathParam(AnnotationHelper.courseId) String courseId,
                     @PathParam(AnnotationHelper.studentId) String studentId,
                     @PathParam("rating") float rating) {
        managementService.rateStudent(courseId, studentId, rating);
    }

    /**
     * Send the contract using the management id and email to student.
     *
     * @param courseId id of course
     * @param studentId id of student
     * @param email the email
     */
    @GetMapping("sendContract")
    public void sendContract(@PathParam(AnnotationHelper.courseId) String courseId,
                             @PathParam(AnnotationHelper.studentId) String studentId,
                             @PathParam("email") String email) {
        managementService.sendContract(courseId, studentId, email);
    }

}
