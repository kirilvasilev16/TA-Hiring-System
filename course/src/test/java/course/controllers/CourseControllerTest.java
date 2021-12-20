package course.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import course.entities.Course;
import course.entities.Student;
import course.services.CommunicationService;
import course.services.CourseService;
import course.services.DateService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@WebMvcTest
class CourseControllerTest {

    @Autowired
    private transient MockMvc mockMvc;
    @Autowired
    private transient CourseController courseController;
    @MockBean
    private transient CourseService courseService;
    @MockBean
    private transient CommunicationService communicationService;
    @MockBean
    private transient DateService dateService;

    private static transient Gson gson = new GsonBuilder().create();

    private transient Course course;
    private transient Set<String> lecturerSet;
    private transient LocalDateTime startingDate;
    private transient String courseId;
    private transient String courseName;
    private transient int courseSize;
    private transient int quarter;
    private transient Set<String> candidateSet;
    private transient Set<String> hireSet;

    private transient String notFoundException = "Could not find a course with id ";
    private transient String student1 = "student1";
    private transient String lecturer1 = "lecturer1";


    @BeforeEach
    void setUp() {

        lecturerSet = new HashSet<>();
        lecturerSet.add(lecturer1);
        startingDate = LocalDateTime.of(LocalDate.of(2021, 11, 7), LocalTime.NOON);
        courseSize = 500;
        quarter = 2;
        courseId = "CSE2115-2021";
        courseName = "SEM";
        course = new Course(courseId, courseName, courseSize, lecturerSet, startingDate, quarter);

        candidateSet = new HashSet<>();
        candidateSet.add(student1);
        course.getCandidateTas().add(student1);

        hireSet = new HashSet<>();
        hireSet.add("student2");
        course.getHiredTas().add("student2");

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.courseController)
                .setMessageConverters(new GsonHttpMessageConverter())
                .build();
    }

    @Test
    void getCourse() throws Exception {

        when(courseService.findByCourseId(courseId)).thenReturn(course);

        this.mockMvc.perform(get("/courses/get?courseId=" + courseId))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(course)));
    }

    @Test
    void getCourseNotFound() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(null);

        Exception exception = assertThrows(NestedServletException.class, () -> {
            this.mockMvc.perform(get("/courses/get?courseId=" + courseId));
        });

        String expect = notFoundException + courseId;
        assertTrue(exception.getMessage().contains(expect));

    }

    @Test
    void getCourseSize() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        this.mockMvc.perform(get("/courses/size?courseId=" + courseId))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(course.getCourseSize())));
    }


    @Test
    void updateCourseSize() throws Exception {

        Integer newSize = 1000;

        when(courseService.findByCourseId(courseId)).thenReturn(course);

        this.mockMvc.perform(put("/courses/updateSize?courseId=" + courseId
                + "&size=" + newSize)
                        .contentType("application/json-patch+json"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        assertEquals(newSize, course.getCourseSize());
        verify(courseService, times(1)).updateCourseSize(courseId, newSize);

    }


    @Test
    void getLecturerSet() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        this.mockMvc.perform(get("/courses/lecturers?courseId=" + courseId))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(lecturerSet)));
    }

    @Test
    void getRequiredTas() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        this.mockMvc.perform(get("/courses/requiredTas?courseId=" + courseId))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(course.getRequiredTas())));
    }

    @Test
    void getTaRecommendationList() throws Exception {

        Map<String, Float> passed = new HashMap<>();
        passed.put("CSE1200", 10.0F);
        Student student = new Student(student1, passed, new HashSet<String>());

        Set<Student> students = new HashSet<>();
        students.add(student);

        when(courseService.findByCourseId(courseId)).thenReturn(course);
        when(communicationService.getStudents(course.getCandidateTas())).thenReturn(students);

        List<String> expect = new ArrayList<>();
        expect.add(student1);

        this.mockMvc.perform(get("/courses/taRecommendations?courseId=" + courseId
                        + "&strategy=grade").header("netId", lecturer1))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(expect)));
    }

    @Test
    void makeCourse() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(null);
        CourseCreationBody courseCreation = new CourseCreationBody(
                courseId, courseName, startingDate, lecturerSet, courseSize, quarter);

        Course expect = new Course(
                courseId, courseName, courseSize, lecturerSet, startingDate, quarter);

        this.mockMvc.perform(post("/courses/makeCourse")
                        .contentType(APPLICATION_JSON)
                        .content(gson.toJson(courseCreation)))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(expect)));

        verify(courseService, times(1)).save(any(Course.class));
    }

    @Test
    void makeCourseExists() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        CourseCreationBody courseCreation = new CourseCreationBody(
                courseId, courseName, startingDate, lecturerSet, courseSize, quarter);

        Exception exception = assertThrows(NestedServletException.class, () -> {
            this.mockMvc.perform(post("/courses/makeCourse")
                    .contentType(APPLICATION_JSON)
                    .content(gson.toJson(courseCreation)));
        });

        String expect = courseId;
        assertTrue(exception.getMessage().contains(expect));

        verify(courseService, never()).save(any(Course.class));
    }

    @Test
    void addCandidateTa() throws Exception {

        LocalDateTime applyDate = LocalDateTime.of(LocalDate.of(2021, 9, 7), LocalTime.NOON);

        when(courseService.findByCourseId(courseId)).thenReturn(course);
        when(dateService.getTodayDate()).thenReturn(applyDate);


        this.mockMvc.perform(put("/courses/addCandidateTa?courseId=" + courseId
                        + "&studentId=student3")
                        .contentType(APPLICATION_JSON)
                        .content(gson.toJson(new HashSet<String>())))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        candidateSet.add("student3");
        assertEquals(candidateSet, course.getCandidateTas());

        verify(courseService, times(1)).save(any(Course.class));
    }

    @Test
    void addCandidateTaInvalidStudentCourse() throws Exception {
        String fakeCourse = "fraudCourse";
        Set<String> studentCourse = new HashSet<>();
        studentCourse.add(fakeCourse);

        when(courseService.findByCourseId(courseId)).thenReturn(course);
        when(courseService.findByCourseId(fakeCourse)).thenReturn(null);

        Exception exception = assertThrows(NestedServletException.class, () -> {
            this.mockMvc.perform(put("/courses/addCandidateTa?courseId=" + courseId
                    + "&studentId=student3")
                    .contentType(APPLICATION_JSON)
                    .content(gson.toJson(studentCourse)));
        });

        String expect = notFoundException + fakeCourse;
        assertTrue(exception.getMessage().contains(expect));

        verify(courseService, never()).save(any(Course.class));
    }

    @Test
    void addCandidateTaTooManyCourses() {

        Set<String> studentCourse = new HashSet<>();
        studentCourse.add(courseId);

        String course2Id = "course2-2021";
        studentCourse.add(course2Id);

        String course3Id = "course3-2021";
        studentCourse.add(course3Id);

        String course4Id = "course4-2021";
        studentCourse.add(course4Id);

        Course course2 = new Course(course2Id, courseName, courseSize, lecturerSet,
                startingDate, quarter);

        Course course3 = new Course(course3Id, courseName, courseSize, lecturerSet,
                startingDate, quarter);

        Course course4 = new Course(course4Id, courseName, courseSize, lecturerSet,
                startingDate, quarter);

        when(courseService.findByCourseId(courseId)).thenReturn(course);
        when(courseService.findByCourseId(course2Id)).thenReturn(course2);
        when(courseService.findByCourseId(course3Id)).thenReturn(course3);
        when(courseService.findByCourseId(course4Id)).thenReturn(course4);

        Exception exception = assertThrows(NestedServletException.class, () -> {
            this.mockMvc.perform(put("/courses/addCandidateTa?courseId=" + courseId
                    + "&studentId=student3")
                    .contentType(APPLICATION_JSON)
                    .content(gson.toJson(studentCourse)));
        });

        String expect = "Quarter " + quarter + " has too many courses";
        assertTrue(exception.getMessage().contains(expect));

        verify(courseService, never()).save(any(Course.class));
    }

    @Test
    void addCandidateTaInvalidStudentHired() throws Exception {

        when(courseService.findByCourseId(courseId)).thenReturn(course);

        Exception exception = assertThrows(NestedServletException.class, () -> {
            this.mockMvc.perform(put("/courses/addCandidateTa?courseId=" + courseId
                    + "&studentId=student2")
                    .contentType(APPLICATION_JSON)
                    .content(gson.toJson(new HashSet<String>())));
        });

        String expect = "Student already hired as TA";
        assertTrue(exception.getMessage().contains(expect));

        verify(courseService, never()).save(any(Course.class));
    }

    @Test
    void addCandidateTaInvalidPastDeadline() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(course);
        when(dateService.getTodayDate()).thenReturn(startingDate);

        Exception exception = assertThrows(NestedServletException.class, () -> {
            this.mockMvc.perform(put("/courses/addCandidateTa?courseId=" + courseId
                    + "&studentId=student3")
                    .contentType(APPLICATION_JSON)
                    .content(gson.toJson(new HashSet<String>())));
        });

        String expect = "Deadline for TA application has past";
        assertTrue(exception.getMessage().contains(expect));

        verify(courseService, never()).save(any(Course.class));
    }


    @Test
    void removeAsCandidate() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        this.mockMvc.perform(delete("/courses/removeAsCandidate?courseId=" + courseId
                        + "&studentId=" + student1))
                .andExpect(status().isOk());

        verify(courseService, times(1)).save(any(Course.class));
        assertTrue(course.getCandidateTas().isEmpty());
    }


    @Test
    void getAverageWorkedHours() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        Set<Float> hours = new HashSet<>();
        hours.add(1.0F);
        when(communicationService.getHoursList(course.getHiredTas(), courseId)).thenReturn(hours);

        this.mockMvc.perform(get("/courses/averageWorkedHours?courseId=" + courseId))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(1.0F)));
    }

    @Test
    void getAverageWorkedHoursNoHired() throws Exception {
        course.getHiredTas().remove("student2");
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        this.mockMvc.perform(get("/courses/averageWorkedHours?courseId=" + courseId))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(0.0F)));

        verify(communicationService, never()).getHoursList(any(Set.class), any(String.class));
    }

    @Test
    void addLecturer() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        this.mockMvc.perform(put("/courses/addLecturer?courseId=" + courseId
                        + "&lecturerId=lecturer2"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        lecturerSet.add("lecturer2");
        assertEquals(lecturerSet, course.getLecturerSet());
        verify(courseService, times(1)).save(any(Course.class));
    }

    @Test
    void getCandidateSet() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        this.mockMvc.perform(get("/courses/candidates?courseId=" + courseId)
                        .header("netId", lecturer1))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(course.getCandidateTas())));
    }

    @Test
    void getTaSet() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        this.mockMvc.perform(get("/courses/tas?courseId=" + courseId)
                        .header("netId", lecturer1))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(course.getHiredTas())));
    }

    @Test
    void hireTa() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        float hours = 1.F;

        this.mockMvc.perform(put("/courses/hireTa?courseId=" + courseId
                        + "&studentId=student1&hours=" + hours)
                        .header("netId", lecturer1))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(communicationService, times(1)).createManagement(courseId, student1, hours);
        verify(courseService, times(1)).save(any(Course.class));

        hireSet.add(student1);
        assertEquals(hireSet, course.getHiredTas());
        assertTrue(course.getCandidateTas().isEmpty());
    }

    @Test
    void hireTaInvalidLecturer() throws Exception {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        float hours = 1.F;

        Exception exception = assertThrows(NestedServletException.class, () -> {
            this.mockMvc.perform(put("/courses/hireTa?courseId=" + courseId
                    + "&studentId=student1&hours=" + hours)
                    .header("netId", "lecturerFraud"));
        });

        String expect = "Lecturer not a staff of this course";
        assertTrue(exception.getMessage().contains(expect));

        verify(communicationService, never()).createManagement(any(String.class),
                any(String.class), any(Float.class));
        verify(courseService, never()).save(any(Course.class));
    }

    @Test
    void hireTaInvalidStudentAlreadyHired() {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        float hours = 1.F;

        Exception exception = assertThrows(NestedServletException.class, () -> {
            this.mockMvc.perform(put("/courses/hireTa?courseId=" + courseId
                    + "&studentId=student2&hours=" + hours)
                    .header("netId", lecturer1));
        });

        String expect = "Student already hired";
        assertTrue(exception.getMessage().contains(expect));

        verify(communicationService, never()).createManagement(any(String.class),
                any(String.class), any(Float.class));
        verify(courseService, never()).save(any(Course.class));
    }

    @Test
    void hireTaInvalidStudentNotInCourse() {
        when(courseService.findByCourseId(courseId)).thenReturn(course);

        float hours = 1.F;

        Exception exception = assertThrows(NestedServletException.class, () -> {
            this.mockMvc.perform(put("/courses/hireTa?courseId=" + courseId
                    + "&studentId=student3&hours=" + hours)
                    .header("netId", lecturer1));
        });

        String expect = "Student not in course";
        assertTrue(exception.getMessage().contains(expect));

        verify(communicationService, never()).createManagement(any(String.class),
                any(String.class), any(Float.class));
        verify(courseService, never()).save(any(Course.class));
    }
}