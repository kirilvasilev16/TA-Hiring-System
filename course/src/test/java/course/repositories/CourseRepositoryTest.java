package course.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import course.entities.Course;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;



@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private transient CourseRepository courseRepository;
    private transient Course course;
    private transient Set<String> lecturerSet;
    private transient LocalDateTime startingDate;
    private transient String courseId;
    private transient String courseName;

    @AfterEach
    void tearDown() {
        courseRepository.deleteAll();
    }

    private transient int courseSize;
    private transient int quarter;

    @BeforeEach
    void setUp() {

        lecturerSet = new HashSet<>();
        lecturerSet.add("lecturer1");
        startingDate = LocalDateTime.of(LocalDate.of(2021, 11, 7), LocalTime.NOON);
        courseSize = 500;
        quarter = 2;
        courseId = "CSE2115-2021";
        courseName = "SEM";
        course = new Course(courseId, courseName, courseSize, lecturerSet, startingDate, quarter);

        courseRepository.save(course);

    }

    @Test
    void findByCourseId() {
        assertEquals(course, courseRepository.findByCourseId(courseId));
    }

    @Test
    void updateCourseSize() {
        assertEquals(courseSize, courseRepository.findByCourseId(courseId).getCourseSize());

        int newSize = 1000;
        courseRepository.updateCourseSize(courseId, newSize);

        assertEquals(newSize, courseRepository.findByCourseId(courseId).getCourseSize());
    }

    @Test
    void updateCandidateTas() {
        assertTrue(courseRepository.findByCourseId(courseId).getCandidateTas().isEmpty());

        Set<String> candidateSet = new HashSet<>();
        candidateSet.add("student1");

        course.getCandidateTas().add("student1");
        courseRepository.save(course);
        //courseRepository.updateCandidateTas(courseId, candidateSet);

        assertEquals(candidateSet, courseRepository.findByCourseId(courseId).getCandidateTas());
    }

    @Test
    void updateLecturerSet() {
        assertEquals(lecturerSet, courseRepository.findByCourseId(courseId).getLecturerSet());

        lecturerSet.add("lecturer2");

        course.getLecturerSet().add("lecturer2");
        courseRepository.save(course);
        //courseRepository.updateLecturerSet(courseId, lecturerSet);

        assertEquals(lecturerSet, courseRepository.findByCourseId(courseId).getLecturerSet());
    }

    @Test
    void updateHireTas() {
        assertTrue(courseRepository.findByCourseId(courseId).getHiredTas().isEmpty());

        Set<String> hireSet = new HashSet<>();
        hireSet.add("student2");

        course.getHiredTas().add("student2");
        courseRepository.save(course);
        //courseRepository.updateHireTas(courseId, hireSet);

        assertEquals(hireSet, courseRepository.findByCourseId(courseId).getHiredTas());
    }
}