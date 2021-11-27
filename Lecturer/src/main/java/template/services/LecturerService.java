package template.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import template.entities.Course;
import template.entities.Lecturer;
import template.entities.Student;
import template.exceptions.CourseNotFoundException;
import template.exceptions.LecturerNotFoundException;
import template.repositories.LecturerRepository;

@Service
public class LecturerService {
    private final transient LecturerRepository lecturerRepository;
    private final transient RestTemplate restTemplate = new RestTemplate();

    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    public List<Lecturer> findAll() {
        return lecturerRepository.findAll();
    }

    public Lecturer findLecturerById(String netId) {
        Optional<Lecturer> lecturer = lecturerRepository.findLecturerByNetId(netId);
        if (lecturer.isEmpty()) throw new LecturerNotFoundException("Lecturer with id " + netId + " was not found.");
        return lecturer.get();
    }

	/**
	 * @param netId of a lecturer
	 * @return list of courses belonging to a lecturer
	 */
    public List<Course> getOwnCourses(String netId) {
        return this.findLecturerById(netId).getCourses();
    }

    public Course getSpecificCourse(String netId, Course course) {
        Lecturer lecturer = this.findLecturerById(netId);
        if (lecturer.getCourses().contains(course)) {
            return course;
        } else {
        	throw new CourseNotFoundException("Course with id " + course.getId() + " is not taught by lecturer");
        }
    }

    public void addLecturer(Lecturer lecturer) {
        lecturerRepository.save(lecturer);
    }

    public List<Student> getCandidateTaList(String netId, Course course) {
        return getSpecificCourse(netId, course).getCandidateTas();
    }

    public void chooseTa(String netId, Course course, String studentNetId) {
        ObjectMapper objectMapper = new ObjectMapper();
        Course neededCourse = this.getSpecificCourse(netId, course);
        try {
            restTemplate.postForEntity("http://localhost:8080/course/" + neededCourse.getId() + "/addTA/" + studentNetId, objectMapper.writeValueAsString(course), Course.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void addSpecificCourse(String netId, Course course) {
        Lecturer lecturer = this.findLecturerById(netId);
        lecturer.getCourses().add(course);
        lecturerRepository.save(lecturer);
    }
}
