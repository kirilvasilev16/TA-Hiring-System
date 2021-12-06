package template.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    /**
     * Find all lectures.
     *
     * @return all lectures
     */
    public List<Lecturer> findAll() {
        return lecturerRepository.findAll();
    }

    /**
     * Find lecturer by id.
     *
     * @param netId of a lecturer
     * @return lecturer with given netId
     */
    public Lecturer findLecturerById(String netId) {
        Optional<Lecturer> lecturer = lecturerRepository.findLecturerByNetId(netId);
        if (lecturer.isEmpty()) {
            throw new LecturerNotFoundException("Lecturer with id " + netId + " was not found.");
        }
        return lecturer.get();
    }

    /**
     * Find all courses of a lecturer.
     * Issue 15.
     *
     * @param netId of a lecturer
     * @return list of courses belonging to a lecturer
     */
    public List<Course> getOwnCourses(String netId) {
        return this.findLecturerById(netId).getCourses();
    }

    /**
     * Find specific course of a lecturer.
     *
     * @param netId netId of a lecturer
     * @param course specific course
     * @return course if lecturer is supervising it
     */
    public Course getSpecificCourse(String netId, Course course) {
        Lecturer lecturer = this.findLecturerById(netId);
        if (lecturer.getCourses().contains(course)) {
            return course;
        } else {
            throw new CourseNotFoundException("Course with id "
                    + course.getId() + " is not taught by lecturer");
        }
    }

    /**
     * Add lecturer.
     *
     * @param lecturer that should be added
     */
    public void addLecturer(Lecturer lecturer) {
        lecturerRepository.save(lecturer);
    }

    /**
     * Get list of candidates for a specific course.
     * Issue 17.
     *
     * @param netId of a lecturer
     * @param course specific course
     * @return list of students that want to be a TA for a course
     */
    public List<Student> getCandidateTaList(String netId, Course course) {
        return this.getSpecificCourse(netId, course).getCandidateTas();
    }

    /**
     * Choose TA for a course. I send post request to a course microservice to save changes there.
     * Issue 18.
     *
     * @param netId of a lecturer
     * @param course specific course
     * @param studentNetId netId of a student
     */
    public void chooseTa(String netId, Course course, String studentNetId) {
        ObjectMapper objectMapper = new ObjectMapper();
        Course neededCourse = this.getSpecificCourse(netId, course);
        try {
            restTemplate.postForEntity("http://localhost:8080/course/" + neededCourse.getId() + "/addTA/" + studentNetId, objectMapper.writeValueAsString(course), Course.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a course to a lecturer.
     * I expect here a request from course microservice with course as the request body.
     *
     * @param netId of a lecturer
     * @param course specific course
     */
    public Lecturer addSpecificCourse(String netId, Course course) {
        Lecturer lecturer = this.findLecturerById(netId);
        lecturer.getCourses().add(course);
        lecturerRepository.save(lecturer);
        return lecturer;
    }

    public double computeAverageRating(String netId, Course course, String studentId) {
        List<Student> students = this.getCandidateTaList(netId, course);
        for (Student student: students) {
            if (student.getId().equals(studentId)) {
                return student.getAverageRating();
            }
        }
        return 0;
    }

    public List<Student> getRecommendation(String netId, Course course) {
        Course c = getSpecificCourse(netId, course);
        if (c == null) return new ArrayList<>();
        Student[] sts = restTemplate.getForObject("http://localhost:8080/course/recommendations" + course.getId(), Student[].class);
        if (sts == null) return new ArrayList<>();
        List<Student> targetList = new ArrayList<>();
        Collections.addAll(targetList, sts);
        return targetList;
    }

    public int getNumberOfNeededTas(String netId, Course course) {
        Course c = getSpecificCourse(netId, course);
        return c.getSize()/20;
    }
}
