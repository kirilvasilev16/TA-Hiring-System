package management.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ManagementTest {

    @Test
    public void testExample() {
        Management exampleTest = new Management("name");
        assertEquals("name", exampleTest.getName());
        exampleTest.setName("newName");
        assertEquals("newName", exampleTest.getName());
    }
}
