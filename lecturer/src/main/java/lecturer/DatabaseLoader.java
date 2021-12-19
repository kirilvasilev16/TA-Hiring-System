package lecturer;

import java.util.ArrayList;
import java.util.List;
import lecturer.entities.Lecturer;
import lecturer.repositories.LecturerRepository;
import org.springframework.stereotype.Service;

@Service
public class DatabaseLoader {

    /**
     * The constructor for the example database loader.
     */
    public DatabaseLoader(LecturerRepository lecturerRepo) {
        List<String> l = new ArrayList<>();
        l.add("CSE22152018");
        Lecturer lecturer = new Lecturer("20304060", "Will", "will@gmail.com", l);
        //System.out.println("Adding");

        lecturerRepo.save(lecturer);

    }
}