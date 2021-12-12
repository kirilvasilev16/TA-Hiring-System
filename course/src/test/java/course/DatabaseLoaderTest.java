package course;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import course.entities.Course;
import course.repositories.CourseRepository;
import java.util.Calendar;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class DatabaseLoaderTest {

    @Test
    void testDatabaseLoader() {

        CourseRepository courseRepository = Mockito.mock(CourseRepository.class);
        DatabaseLoader dbLoader = new DatabaseLoader(courseRepository);

        Calendar date = new Calendar.Builder().setDate(2021, 11, 7).build();
        Course course = new Course("CSE5000", "Sample", 5, new HashSet<String>(), date, 2);

        verify(courseRepository, times(1)).save(course);
    }

}