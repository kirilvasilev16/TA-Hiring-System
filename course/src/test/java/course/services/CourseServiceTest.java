package course.services;

import course.entities.Course;
import course.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CourseServiceTest {

    private transient CourseRepository courseRepository;
    private transient CourseService courseService;

    private transient Course course1;
    private transient Set<String> lecturerSet;
    private transient Calendar startingDate;
    private transient String courseId;
    private transient String courseName;
    private transient int courseSize;
    private transient int quarter;

    @BeforeEach
    void setUp() {
        courseRepository = Mockito.mock(CourseRepository.class);
        courseService = new CourseService(courseRepository);

        lecturerSet = new HashSet<>();
        lecturerSet.add("lecturer1");
        startingDate = new Calendar.Builder().setDate(2021, 11, 7).build();
        courseSize = 500;
        quarter = 2;
        courseId = "CSE2115-2021";
        courseName = "SEM";
        course1 = new Course(courseId, courseName, courseSize, lecturerSet, startingDate, quarter);




    }

    @Test
    void findByCourseId() {
        Mockito.when(courseRepository.findByCourseId(courseId)).thenReturn(course1);

        assertEquals(course1, courseService.findByCourseId(courseId));
    }

    @Test
    void findByCourseIdFalse() {
        String missingCourse = "CSE2115-2020";
        Mockito.when(courseRepository.findByCourseId(missingCourse)).thenReturn(null);

        assertEquals(null, courseService.findByCourseId(missingCourse));
    }

    @Test
    void save() {
        courseService.save(course1);

        verify(courseRepository, times(1)).save(course1);
    }

    @Test
    void updateCourseSize() {
        int newSize = 200;

        courseService.updateCourseSize(courseId, newSize);

        verify(courseRepository, times(1)).updateCourseSize(courseId, newSize);
    }
}