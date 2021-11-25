package template;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseExampleTest {

    @Test
    public void testGettersSetters() {
        CourseExample exampleTest = new CourseExample("name");
        assertEquals("name", exampleTest.getName());
        exampleTest.setName("newName");
        assertEquals("newName", exampleTest.getName());
    }
}
