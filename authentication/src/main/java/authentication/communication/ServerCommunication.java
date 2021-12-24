package authentication.communication;

import authentication.entities.ResponseObj;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;

public class ServerCommunication extends authentication.communication.Request {
    public ServerCommunication() {}

    /**
     * Retrieves a quote from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public ResponseObj getRequest(String path) throws IOException {
        ResponseObj response = get("http://localhost:8761" + path);
        return response;
    }

    public ResponseObj putRequest(String path, String body) throws IOException {
        ResponseObj response = put("http://localhost:8761" + path, body);
        return response;
    }

    public ResponseObj postRequest(String path, String body) throws IOException {
        ResponseObj response = post("http://localhost:8761" + path, body);
        return response;
    }
}
