package authentication.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ResponseObjTest {
    private transient ResponseObj resp;
    private transient String name = "result";
    private transient int statusCode = 200;

    @BeforeEach
    void setUp() {
        resp = new ResponseObj(name, statusCode);
    }

    @Test
    void constructor() {
        assertNotNull(resp);
    }

    @Test
    void getResult() {
        assertEquals(name, resp.getResult());
    }

    @Test
    void getStatusCode() {
        assertEquals(200, resp.getStatusCode());
    }

    @Test
    void setResult() {
        resp.setResult("myName");
        assertEquals("myName", resp.getResult());
    }

    @Test
    void setStatusCode() {
        resp.setStatusCode(404);
        assertEquals(404, resp.getStatusCode());
    }
}
