package authentication.communication;

import authentication.entities.Management;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class ServerCommunication extends authentication.communication.Request {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    /**
     * Retrieves a quote from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getRequest(String path) throws IOException {
        String response = get("http://localhost:8761" + path);
        return response;
    }
    public static String putRequest(String path) throws IOException {
        String response = put("http://localhost:8761" + path);
        return response;
    }
    public static String postRequest(String path) throws IOException {
        String response = post("http://localhost:8761" + path);
        return response;
    }
//    public static Collection<Management> getManagementRequest(String path) throws IOException {
//
//        String response = get("http://localhost:8761" + path);
//
//        if (response.substring(0,1).equals("[")) {
//            Type collectionType = new TypeToken<Collection<Management>>(){}.getType();
//            Collection<Management> m = gson.fromJson(response, collectionType);
//            return m;
//        }else {
//            Type collectionType = new TypeToken<Management>(){}.getType();
//            Management m = gson.fromJson(response, collectionType);
//            return new ArrayList<>(Arrays.asList(m));
//        }
//
//    }
//
//    public static void putManagementRequest(String path) throws IOException {
//        String response = put("http://localhost:8761" + path);
//        System.out.println(response);
//    }
//    public static Management postManagementRequest(String path) throws IOException {
//        String response = post("http://localhost:8761" + path);
//            Type collectionType = new TypeToken<Management>(){}.getType();
//            Management m = gson.fromJson(response, collectionType);
//            return m;
//
//    }
}
