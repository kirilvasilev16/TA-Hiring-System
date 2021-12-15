package course.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import course.entities.Course;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashSet;
import org.junit.jupiter.api.Test;


public class CourseExampleTest {

    @Test
    public void testGettersSetters() {
        LocalDateTime date = LocalDateTime.of(LocalDate.of(2021, 11, 7), LocalTime.NOON);
        Course c1 = new Course("CSE1000", "TestName", 5, new HashSet<>(), date, 3);

        assertEquals("TestName", c1.getName());
        /*CourseExample exampleTest = new CourseExample("name");
        assertEquals("name", exampleTest.getName());
        exampleTest.setName("newName");
        assertEquals("newName", exampleTest.getName());*/
    }
}
