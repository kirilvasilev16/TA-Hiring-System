import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import template.entities.Course;
import template.entities.Lecturer;
import template.exceptions.CourseNotFoundException;
import template.exceptions.LecturerNotFoundException;
import template.repositories.LecturerRepository;
import template.services.LecturerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LecturerServiceTest {
    transient LecturerRepository lecturerRepository;
    transient LecturerService lecturerService;
    transient Lecturer lecturer1;
    transient Lecturer lecturer2;
    transient List<Course> courses = new ArrayList<>();
    transient Course course = new Course(1L, new ArrayList<>());
    transient ArrayList<Lecturer> lecturers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        courses.add(course);

        lecturerRepository = Mockito.mock(LecturerRepository.class);
        lecturerService = new LecturerService(lecturerRepository);

        lecturer1 = new Lecturer("1", "name", "password", "email", courses);
        lecturer2 = new Lecturer("2", "name", "password", "email", new ArrayList<>());

        lecturers.add(lecturer1);
        lecturers.add(lecturer2);

        Mockito.when(lecturerRepository.findLecturerByNetId("1")).thenReturn(java.util.Optional.ofNullable(lecturer1));
        Mockito.when(lecturerRepository.findLecturerByNetId("2")).thenReturn(java.util.Optional.ofNullable(lecturer2));
        Mockito.when(lecturerRepository.findLecturerByNetId("3")).thenReturn(Optional.empty());
        Mockito.when(lecturerRepository.save(lecturer1)).thenReturn(lecturer1);
    }

    @Test
    void findAll() {
        Mockito.when(lecturerRepository.findAll()).thenReturn(lecturers);
        assertEquals(lecturers, lecturerService.findAll());
    }

    @Test
    void findById() {
        assertEquals(lecturer1, lecturerService.findLecturerById("1"));
    }

    @Test
    void findByIdNullable() {
        assertThrows(LecturerNotFoundException.class, () -> lecturerService.findLecturerById("3"));
    }

    @Test
    void getOwnCourses() {
        assertEquals(courses, lecturerService.getOwnCourses("1"));
    }

    @Test
    void getSpecificCourse() {
        assertEquals(course, lecturerService.getSpecificCourse("1", course));
    }

    @Test
    void getSpecificNullCourse() {
        assertThrows(CourseNotFoundException.class, () -> lecturerService.getSpecificCourse("1", new Course()));
    }

    @Test
    void addLecturer() {
        lecturerService.addLecturer(lecturer2);
        ArgumentCaptor<Lecturer> argument = ArgumentCaptor.forClass(Lecturer.class);
        Mockito.verify(lecturerRepository).save(argument.capture());
        assertEquals(lecturer2.getNetId(), argument.getValue().getNetId());
    }

    @Test
    void getTaCandidates() {
        assertEquals(0, lecturerService.getCandidateTaList("1", course).size());
    }

    @Test
    void addCourse() {
        lecturerService.addSpecificCourse("2", course);
        ArgumentCaptor<Lecturer> argument = ArgumentCaptor.forClass(Lecturer.class);
        Mockito.verify(lecturerRepository).save(argument.capture());
        assertEquals(1, lecturer2.getCourses().size());
    }
}
