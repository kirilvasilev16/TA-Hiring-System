package management.controllers;

import management.entities.Management;
import management.services.ManagementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping(value = "findAll")
    public List<Management> findAll() {
        return managementService.findAll();
    }
}
