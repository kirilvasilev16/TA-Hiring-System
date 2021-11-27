package management.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import management.entities.Management;
import management.services.ManagementService;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
     * @param id the id
     * @return the management object
     */
    @GetMapping("get")
    public Management getOne(@PathParam("id") long id) {
        return managementService.getOne(id);
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
    public Management create(@PathParam("courseId") long courseId,
                                 @PathParam("studentId") long studentId,
                                 @PathParam("amountOfHours") float amountOfHours) {
        return managementService.createManagement(courseId, studentId, amountOfHours);
    }

    /**
     * Declare working hours for student for a given course.
     *
     * @param id the id of the management object
     * @param hours hours declared
     */
    @PutMapping("declareHours")
    public void declareHours(@PathParam("id") long id,
                             @PathParam("hours") long hours) {
        managementService.declareHours(id, hours);
    }

    /**
     * Approve working hours for student for a given course.
     *
     * @param id the id of the management object
     * @param hours hours declared
     */
    @PutMapping("approveHours")
    public void approveHours(@PathParam("id") long id,
                             @PathParam("hours") long hours) {
        managementService.approveHours(id, hours);
    }

    /**
     * Rate a student for a given course.
     *
     * @param id the id of the management object
     * @param rating new rating
     */
    @PutMapping("rate")
    public void rate(@PathParam("id") long id,
                             @PathParam("rating") float rating) {
        managementService.rateStudent(id, rating);
    }

}
