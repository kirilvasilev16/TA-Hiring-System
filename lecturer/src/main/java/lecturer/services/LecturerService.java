package lecturer.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import lecturer.entities.Course;
import lecturer.entities.Hours;
import lecturer.entities.Lecturer;
import lecturer.entities.Student;
import lecturer.exceptions.LecturerNotFoundException;
import lecturer.exceptions.OwnNoPermissionException;
import lecturer.repositories.LecturerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Here I suppress duplicates as all exceptions usually have similar but not same messages.
 */

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@Service
public class LecturerService {
    private final transient LecturerRepository lecturerRepository;
    private final transient CommunicationService communicationService;

    public LecturerService(LecturerRepository lecturerRepository,
                           CommunicationService communicationService) {
        this.lecturerRepository = lecturerRepository;
        this.communicationService = communicationService;
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
     * @param lecturerId of a lecturer
     * @return lecturer with given lecturerId
     */
    public Lecturer findLecturerById(String lecturerId) {
        Optional<Lecturer> lecturer = lecturerRepository.findLecturerByLecturerId(lecturerId);
        if (lecturer.isEmpty()) {
            throw new LecturerNotFoundException("Lecturer with id "
                    + lecturerId + " was not found");
        }
        return lecturer.get();
    }

    /**
     * Find all courses of a lecturer.
     *
     * @param lecturerId of a lecturer
     * @return list of courses belonging to a lecturer
     */
    public List<String> getOwnCourses(String lecturerId) {
        return this.findLecturerById(lecturerId).getCourses();
    }

    /**
     * Verify if course belongs to a lecturer.
     *
     * @param lecturerId net id of lecturer
     * @param courseId course id
     */
    public void verifyThatApplicableCourse(String lecturerId, String courseId) {
        if (this.getOwnCourses(lecturerId).contains(courseId)) {
            return;
        }
        throw new OwnNoPermissionException("Course with id "
                + courseId + " is not taught by lecturer");
    }

    /**
     * Find specific course of a lecturer.
     *
     * @param lecturerId lecturerId of a lecturer
     * @param courseId specific course
     * @return course if lecturer is supervising it
     */
    public Course getSpecificCourseOfLecturer(String lecturerId, String courseId) {
        this.verifyThatApplicableCourse(lecturerId, courseId);
        ResponseEntity<Course> course = communicationService.getCourse(courseId,
                "The course with id " + courseId + " could not be found.");
        return course.getBody();
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
     *
     * @param lecturerId of a lecturer
     * @param courseId specific course
     * @return list of students that want to be a TA for a course
     */
    public Set<String> getCandidateTaList(String lecturerId, String courseId) {
        return this.getSpecificCourseOfLecturer(lecturerId, courseId).getCandidateTas();
    }

    /**
     * Choose TA for a course. I send post request to a course microservice to save changes there.
     *
     * @param lecturerId of a lecturer
     * @param courseId specific course
     * @param studentlecturerId lecturerId of a student
     */
    public boolean chooseTa(String lecturerId, String courseId, String studentlecturerId,
                         float hours) {
        this.verifyThatApplicableCourse(lecturerId, courseId);
        Course courseEntity = this.getSpecificCourseOfLecturer(lecturerId, courseId);
        if ((int) Math.ceil(courseEntity.getCourseSize() / 20.0)
                <= courseEntity.getHiredTas().size()) {
            throw new OwnNoPermissionException("You cannot hire this student "
                    + "- the limit is exceeded.");
        }
        communicationService.choose(lecturerId, courseId,
                studentlecturerId, hours,
                "The course with id " + courseId
                        + " could not be found.");
        return true;
    }

    /**
     * Add a course to a lecturer.
     * I expect here a request from course microservice with course as the request body.
     *
     * @param lecturerId of a lecturer
     * @param courseId specific course id
     */
    public Lecturer addSpecificCourse(String lecturerId, String courseId) {
        Lecturer lecturer = this.findLecturerById(lecturerId);
        lecturer.getCourses().add(courseId);
        lecturerRepository.save(lecturer);
        return lecturer;
    }

    /**
     * Get recommendations of students for specific course.
     *
     * @param lecturerId if of a lecturer
     * @param courseId specific course
     * @return list of recommended students
     */
    public List<Student> getRecommendation(String lecturerId, String courseId, String strategy) {
        this.verifyThatApplicableCourse(lecturerId, courseId);
        ResponseEntity<List<String>> sts = communicationService.studentIds(lecturerId,
                courseId, strategy,
                "Student ids are not available");
        ResponseEntity<List<Student>> stL = communicationService.students(sts,
                "List of students could not be found.");
        return stL.getBody();
    }

    /**
     * Get number of needed Ta's for course.
     *
     * @param lecturerId id of a lecturer
     * @param courseId specific course
     * @return number of needed Ta's
     */
    public int getNumberOfNeededTas(String lecturerId, String courseId) {
        Course course = this.getSpecificCourseOfLecturer(lecturerId, courseId);
        return (int) Math.ceil(course.getCourseSize() / 20.0);
    }

    /**
     * Approve hours for a student.
     *
     * @param lecturerId of a lecturer
     * @param hours includes courseId, studentId and hours
     */
    public boolean approveHours(String lecturerId, List<Hours> hours) {
        if (hours.isEmpty()) {
            throw new EntityNotFoundException();
        }
        this.verifyThatApplicableCourse(lecturerId, hours.get(0).getCourseId());
        communicationService.approveHours(hours,
                "The system failed to approve hours.");
        return true;
    }

    /**
     * Disapprove hours for a student.
     *
     * @param lecturerId of a lecturer
     * @param hours includes courseId, studentId and hours
     */
    public boolean disapproveHours(String lecturerId, List<Hours> hours) {
        if (hours.isEmpty()) {
            throw new EntityNotFoundException();
        }
        this.verifyThatApplicableCourse(lecturerId, hours.get(0).getCourseId());
        communicationService.disapproveHours(hours,
                "The system failed to disapprove hours.");
        return true;
    }

    /**
     * Gets average rating of a student.
     *
     * @param lecturerId of a lecturer
     * @param course specific course
     * @param studentId id of a student
     * @return average rating of a student
     */
    public float getAverage(String lecturerId, String course, String studentId) {
        Set<String> students = this.getCandidateTaList(lecturerId, course);
        if (students.contains(studentId)) {
            ResponseEntity<Float> d = communicationService.average(studentId,
                    "The rating of student with id " + studentId
                            + " could not be found.");
            return d.getBody();
        }
        throw new EntityNotFoundException();
    }

    /**
     * Rate student.
     *
     * @param netId of a lecturer
     * @param courseId course id
     * @param studentId student id
     * @param rating rating
     */
    public boolean rateTa(String netId, String courseId, String studentId, float rating) {
        verifyThatApplicableCourse(netId, courseId);
        Set<String> ids = this.getSpecificCourseOfLecturer(netId, courseId).getHiredTas();
        if (ids.contains(studentId)) {
            communicationService.rate(courseId, studentId, rating,
                    "Your rating could not be saved.");
            return true;
        } else {
            throw new OwnNoPermissionException("You cannot rate this student.");
        }
    }

    /**
     * Find a student.
     *
     * @param lecturerId lecturer id
     * @param courseId course id
     * @param studentId student id
     * @return student
     */
    public Student viewStudent(String lecturerId, String courseId, String studentId) {
        Course course = this.getSpecificCourseOfLecturer(lecturerId, courseId);
        Set<String> students = course.getCandidateTas();
        Set<String> hired = course.getHiredTas();
        if (hired.contains(studentId) || students.contains(studentId)) {
            ResponseEntity<Student> studentResponseEntity = communicationService.view(studentId,
                    "The student with id " + studentId
                            + " could not be found.");
            return studentResponseEntity.getBody();
        }
        throw new OwnNoPermissionException("You cannot access information about the student.");
    }
}
