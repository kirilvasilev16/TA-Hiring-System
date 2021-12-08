package management.serializers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import management.entities.Management;
import org.junit.jupiter.api.Test;

class ManagementSerializerTest {

    private static Gson gson = new Gson();

    @Test
    void serialize() {
        Management management1 = new Management("CSE1200", "kvasilev", 120);
        management1.setId(1);
        management1.setRating(10.0f);
        management1.setDeclaredHours(20);
        management1.setApprovedHours(50);

        String json = "{\"id\":1,\"courseId\":\"CSE1200\",\"studentId\":"
                + "\"kvasilev\",\"amountOfHours\""
                + ":120.0,\"approvedHours\":50.0,\"declaredHours\":20.0,\"rating\":10.0}";

        assertEquals(json, gson.toJson(management1, Management.class));
    }
}