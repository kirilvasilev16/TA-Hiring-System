package course.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import course.entities.Management;
import course.entities.Student;
import course.exceptions.FailedContractCreationException;
import course.exceptions.FailedGetHoursException;
import course.exceptions.FailedGetStudentListException;
import course.exceptions.FailedGetStudentRatingsException;
import course.exceptions.FailedUpdateStudentEmploymentException;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class CommunicationServiceTest {
    private final transient String student1 = "student1";
    transient CommunicationService communicationService;
    transient Gson gson;
    transient String studentIdSample;

    @BeforeEach
    void setUp() {
        communicationService = new CommunicationService();
        gson = new GsonBuilder().create();
        studentIdSample = "p@parker";
    }

    @Test
    void setClient() {
        HttpClient client = HttpClient.newBuilder().build();
        communicationService.setClient(client);
        assertEquals(client, communicationService.getClient());
    }

    @Test
    void getRatingsOneStudent() throws IOException, InterruptedException {
        Set<Student> candidateSet = new HashSet<>();
        Student s = new Student(studentIdSample, null, null);
        candidateSet.add(s);

        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Management m = new Management();
        m.setRating(7.0f);
        Mockito.when(response.body()).thenReturn(gson.toJson(m));

        communicationService.setClient(client);

        assertEquals(7.0f, communicationService
                .getRatings(candidateSet, "CSE2000-2021").get(s));

    }

    @Test
    void getRatingsFail() throws IOException, InterruptedException {
        Set<Student> candidateSet = new HashSet<>();
        Student s = new Student(studentIdSample, null, null);
        candidateSet.add(s);

        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenThrow(new IOException());

        Management m = new Management();
        m.setRating(7.0f);
        Mockito.when(response.body()).thenReturn(gson.toJson(m));

        communicationService.setClient(client);

        assertThrows(FailedGetStudentRatingsException.class, () -> {
            communicationService.getRatings(candidateSet, "CSE2000-2021").get(s);
        });
    }

    @Test
    void getRatingsBadStatus() throws IOException, InterruptedException {
        Set<Student> candidateSet = new HashSet<>();
        Student s = new Student(studentIdSample, null, null);
        candidateSet.add(s);

        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(300);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Management m = new Management();
        m.setRating(7.0f);
        Mockito.when(response.body()).thenReturn(gson.toJson(m));

        communicationService.setClient(client);

        assertEquals(7.0f, communicationService.getRatings(candidateSet, "CSE2000-2021").get(s));
    }

    @Test
    void createManagementTest() throws IOException, InterruptedException {
        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.<HttpResponse<? extends String>>
                        when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Management m = new Management();
        m.setManagementId("id1");
        Mockito.when(response.body()).thenReturn(gson.toJson(m));

        communicationService.setClient(client);

        assertEquals("id1",
                communicationService.createManagement("cid", "sid", 12.5f)
                        .getManagementId());

    }

    @Test
    void createManagementFail() throws IOException, InterruptedException {
        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenThrow(new IOException());

        Management m = new Management();
        m.setManagementId("id1");
        Mockito.when(response.body()).thenReturn(gson.toJson(m));

        communicationService.setClient(client);

        assertThrows(FailedContractCreationException.class, () -> {
            communicationService.createManagement("cid", "sid", 12.5f);
        });
    }

    @Test
    void getStudentsOneStudent() throws IOException, InterruptedException {
        Set<String> candidateSet = new HashSet<>();
        candidateSet.add(studentIdSample);

        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Student s = new Student(studentIdSample, null, null);
        Mockito.when(response.body()).thenReturn(gson.toJson(s));

        communicationService.setClient(client);

        assertEquals(1, communicationService.getStudents(candidateSet).size());
    }

    @Test
    void getStudentsBadStatus() throws IOException, InterruptedException {
        Set<String> candidateSet = new HashSet<>();
        candidateSet.add(studentIdSample);

        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(300);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Student s = new Student(studentIdSample, null, null);
        Mockito.when(response.body()).thenReturn(gson.toJson(s));

        communicationService.setClient(client);

        assertEquals(1, communicationService.getStudents(candidateSet).size());
    }

    @Test
    void getStudentsFail() throws IOException, InterruptedException {
        Set<String> candidateSet = new HashSet<>();
        candidateSet.add(studentIdSample);

        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(300);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenThrow(new IOException());

        Student s = new Student(studentIdSample, null, null);
        Mockito.when(response.body()).thenReturn(gson.toJson(s));

        communicationService.setClient(client);

        assertThrows(FailedGetStudentListException.class, () -> {
            communicationService.getStudents(candidateSet);
        });
    }

    @Test
    void getHoursListTest() throws IOException, InterruptedException {
        Set<String> hiredTas = new HashSet<>();
        hiredTas.add(studentIdSample);

        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Mockito.when(response.body()).thenReturn(gson.toJson(7.0f));

        communicationService.setClient(client);

        assertTrue(communicationService.getHoursList(hiredTas, "Cid").contains(7.0f));
    }

    @Test
    void getHoursListBadStatus() throws IOException, InterruptedException {
        Set<String> hiredTas = new HashSet<>();
        hiredTas.add(studentIdSample);

        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(300);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Mockito.when(response.body()).thenReturn(gson.toJson(7.0f));

        communicationService.setClient(client);

        assertTrue(communicationService.getHoursList(hiredTas, "Cid").contains(7.0f));
    }

    @Test
    void getHoursListFail() throws IOException, InterruptedException {
        Set<String> hiredTas = new HashSet<>();
        hiredTas.add(studentIdSample);

        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(300);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenThrow(new IOException());

        Mockito.when(response.body()).thenReturn(gson.toJson(7.0f));

        communicationService.setClient(client);

        assertThrows(FailedGetHoursException.class, () -> {
            communicationService.getHoursList(hiredTas, "Cid");
        });
    }

    @Test
    void updateStudentEmployment() throws IOException, InterruptedException {
        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.<HttpResponse<? extends String>>
                        when(client.send(Mockito.any(), Mockito.any()))
                .thenReturn(response);

        Student s = new Student(student1, new HashMap<String, Float>(), new HashSet<String>());
        Mockito.when(response.body()).thenReturn(gson.toJson(s));

        communicationService.setClient(client);

        assertTrue(communicationService.updateStudentEmployment(student1, "course1"));
    }

    @Test
    void updateStudentEmploymentFail() throws IOException, InterruptedException {
        HttpClient client = Mockito.mock(HttpClient.class);
        HttpResponse<String> response = Mockito.mock(HttpResponse.class);

        Mockito.when(response.statusCode()).thenReturn(200);
        Mockito.<HttpResponse<? extends String>>when(client.send(Mockito.any(), Mockito.any()))
                .thenThrow(new IOException());

        Student s = new Student(student1, new HashMap<String, Float>(), new HashSet<String>());
        Mockito.when(response.body()).thenReturn(gson.toJson(s));

        communicationService.setClient(client);

        assertThrows(FailedUpdateStudentEmploymentException.class, () -> {
            communicationService.updateStudentEmployment(student1, "course1");
        });
    }


}