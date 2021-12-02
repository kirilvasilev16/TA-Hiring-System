package template;

import course.entities.Course;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseExampleTest {

    @Test
    public void testGettersSetters() {
        Course c1 = new Course("CSE1000", "TestName", 5,new HashSet<>(), new Date());
        assertEquals("TestName", c1.getName());
        /*CourseExample exampleTest = new CourseExample("name");
        assertEquals("name", exampleTest.getName());
        exampleTest.setName("newName");
        assertEquals("newName", exampleTest.getName());*/
    }
}
