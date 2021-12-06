package management.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import management.entities.Management;


public class ManagementSerializer implements JsonSerializer<Management> {

    @Override
    public JsonElement serialize(Management management, Type typeOfSrc,
                                 JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", management.getId());
        jsonObject.addProperty("courseId", management.getCourseId());
        jsonObject.addProperty("studentId", management.getStudentId());

        jsonObject.addProperty("amountOfHours", management.getAmountOfHours());
        jsonObject.addProperty("approvedHours", management.getApprovedHours());
        jsonObject.addProperty("declaredHours", management.getDeclaredHours());

        jsonObject.addProperty("rating", management.getRating());

        return jsonObject;
    }
}
