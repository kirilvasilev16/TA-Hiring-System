package management.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import management.entities.Management;
import management.services.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;


public class ManagementSerializer extends StdSerializer<Management> {

    static final long serialVersionUID = 1L;

    @Autowired
    private ManagementService managementService;

    public ManagementSerializer() {
        this(null);
    }

    public ManagementSerializer(Class<Management> t) {
        super(t);
    }

    @Override
    public void serialize(Management management, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();

        gen.writeNumberField("id", management.getId());
        gen.writeStringField("courseId", management.getCourseId());
        gen.writeStringField("studentId", management.getStudentId());

        gen.writeNumberField("amountOfHours", management.getAmountOfHours());
        gen.writeNumberField("approvedHours", management.getApprovedHours());
        gen.writeNumberField("declaredHours", management.getDeclaredHours());

        gen.writeNumberField("rating", management.getRating());

        gen.writeEndObject();
    }
}
