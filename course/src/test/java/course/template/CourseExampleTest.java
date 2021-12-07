package course.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import course.entities.Course;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import org.junit.jupiter.api.Test;


public class CourseExampleTest {

    @Test
    public void testGettersSetters() {
        Calendar date = new Calendar.Builder().setDate(2021, 11, 7).build();
        Course c1 = new Course("CSE1000", "TestName", 5, new HashSet<>(), date);
        assertEquals("TestName", c1.getName());
        /*CourseExample exampleTest = new CourseExample("name");
        assertEquals("name", exampleTest.getName());
        exampleTest.setName("newName");
        assertEquals("newName", exampleTest.getName());*/
    }
}
