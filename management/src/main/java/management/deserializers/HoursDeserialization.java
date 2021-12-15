package management.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import management.entities.Hours;

public class HoursDeserialization implements JsonDeserializer<Hours> {

    /**
     * Custom deserializer used by gson.
     *
     * @param json the input json from server
     * @param typeOfT the type of object to deserialize to
     * @param context context for deserialization
     * @return deserialized Hours object
     * @throws JsonParseException exception if parsing the json to object is impossible
     */
    @Override
    public Hours deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String courseId = jsonObject.get("courseId").getAsString();
        String studentId = jsonObject.get("studentId").getAsString();
        float hours = jsonObject.get("hours").getAsFloat();

        return new Hours(courseId, studentId, hours);
    }
}
