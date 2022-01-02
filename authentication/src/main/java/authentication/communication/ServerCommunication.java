package authentication.communication;

import authentication.entities.ResponseObj;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;

public class ServerCommunication extends authentication.communication.Request {

    private transient Request request;

    public ServerCommunication(Request request) {
        this.request = request;
    }

    /**
     * Retrieves a quote from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public ResponseObj getRequest(String path) throws IOException {
        ResponseObj response = request.get("http://localhost:8761" + path);
        return response;
    }

    public ResponseObj putRequest(String path, String body) throws IOException {
        ResponseObj response = request.put("http://localhost:8761" + path, body);
        return response;
    }

    public ResponseObj postRequest(String path, String body) throws IOException {
        ResponseObj response = request.post("http://localhost:8761" + path, body);
        return response;
    }
}
