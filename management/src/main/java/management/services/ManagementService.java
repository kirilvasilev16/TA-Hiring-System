package management.services;

import management.entities.Management;
import management.repositories.ManagementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagementService {

    private final transient ManagementRepository managementRepository;

    public ManagementService(ManagementRepository managementRepository) {
        this.managementRepository = managementRepository;
    }

    /**
     * Finds all managements stored in the database.
     *
     * @return the list
     */
    public List<Management> findAll() {
        return managementRepository.findAll();
    }
}
