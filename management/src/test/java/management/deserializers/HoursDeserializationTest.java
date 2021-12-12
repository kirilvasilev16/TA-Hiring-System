package management.deserializers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import management.entities.Hours;
import org.junit.jupiter.api.Test;

class HoursDeserializationTest {

    private static Gson gson = new Gson();

    @Test
    void deserializeOne() {

        String json = "{\"courseId\":CSE1200,\"studentId\":kvasilev,\"hours\":20.0}";

        Hours hours = gson.fromJson(json, Hours.class);

        assertEquals("CSE1200", hours.getCourseId());
        assertEquals("kvasilev", hours.getStudentId());
        assertEquals(20, hours.getAmountOfHours());
    }

    @Test
    void deserializeMany() {

        String json = "[{\"courseId\":CSE1200,\"studentId\":kvasilev,\"hours\""
                + ":20.0}, {\"courseId\":CSE1300,\"studentId\":aatanasov,\"hours\""
                + ":140.0}]";

        ArrayList<Hours> hours = gson.fromJson(json, new TypeToken<ArrayList<Hours>>(){}.getType());

        assertEquals("CSE1200", hours.get(0).getCourseId());
        assertEquals("kvasilev", hours.get(0).getStudentId());
        assertEquals(20.0f, hours.get(0).getAmountOfHours());

        assertEquals("CSE1300", hours.get(1).getCourseId());
        assertEquals("aatanasov", hours.get(1).getStudentId());
        assertEquals(140.0f, hours.get(1).getAmountOfHours());
    }
}