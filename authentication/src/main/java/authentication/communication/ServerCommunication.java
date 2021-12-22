package authentication.communication;

import authentication.entities.ResponseObj;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;

public class ServerCommunication extends authentication.communication.Request {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

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
    /*
    public static Collection<Management> getManagementRequest(String path) throws IOException {

        String response = get("http://localhost:8761" + path);

        if (response.substring(0,1).equals("[")) {
            Type collectionType = new TypeToken<Collection<Management>>(){}.getType();
            Collection<Management> m = gson.fromJson(response, collectionType);
            return m;
        }else {
            Type collectionType = new TypeToken<Management>(){}.getType();
            Management m = gson.fromJson(response, collectionType);
            return new ArrayList<>(Arrays.asList(m));
        }

    }

    public static void putManagementRequest(String path) throws IOException {
        String response = put("http://localhost:8761" + path);
        System.out.println(response);
    }
    public static Management postManagementRequest(String path) throws IOException {
        String response = post("http://localhost:8761" + path);
            Type collectionType = new TypeToken<Management>(){}.getType();
            Management m = gson.fromJson(response, collectionType);
            return m;

    }
    */
}
