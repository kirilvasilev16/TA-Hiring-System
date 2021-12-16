package lecturer;

import lecturer.entities.Lecturer;
import lecturer.repositories.LecturerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class DatabaseLoader {

    /**
     * The constructor for the example database loader.
     */
    public DatabaseLoader(LecturerRepository lecturerRepo) {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(2021, 11, 7), LocalTime.NOON);
        List<String> l = new ArrayList<>();
        l.add("CSE22152018");
        Lecturer lecturer = new Lecturer("20304060", "Will", "01234", "will@gmail.com", l);
        //System.out.println("Adding");

        lecturerRepo.save(lecturer);

    }
}