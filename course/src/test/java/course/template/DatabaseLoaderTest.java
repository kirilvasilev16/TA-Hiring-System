package course.template;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import course.DatabaseLoader;
import course.entities.Course;
import course.repositories.CourseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class DatabaseLoaderTest {

    @Test
    void testDatabaseLoader() {
        CourseRepository courseRepository = Mockito.mock(CourseRepository.class);
        new DatabaseLoader(courseRepository);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

}