package student.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentTest {

    private transient Student student;
    private final transient String netId = "ohageman";
    private final transient String name = "Oisín";
    private transient Map<String, Float> passedCourses;
    private transient Set<String> candidateCourses;
    private transient Set<String> taCourses;

    @BeforeEach
    void setUp() {
        passedCourses = new HashMap<>();
        passedCourses.put("CSE2115", 10.0f);
        passedCourses.put("CSE1400", 10.0f);
        passedCourses.put("CSE1105", 10.0f);
        student = new Student(netId, name);
        candidateCourses = new HashSet<>();
        candidateCourses.add("CSE2115-2022");
        candidateCourses.add("CSE1400-2021");
        taCourses = new HashSet<>();
        taCourses.add("CSE1105-2022");
        student.setPassedCourses(passedCourses);
        student.setCandidateCourses(candidateCourses);
        student.setTaCourses(taCourses);
    }

    @Test
    void constructorTest() {
        assertNotNull(student);
    }

    @Test
    void emptyConstructorTest() {
        Student student = new Student();
        assertNotNull(student);
    }

    @Test
    void netIdGetterTest() {
        assertEquals(netId, student.getNetId());
    }

    @Test
    void netIdSetterTest() {
        student.setNetId("newnetid");
        assertEquals("newnetid", student.getNetId());
    }

    @Test
    void nameGetterTest() {
        assertEquals(name, student.getName());
    }

    @Test
    void nameSetterTest() {
        student.setName("NewName");
        assertEquals("NewName", student.getName());
    }

    @Test
    void passedCoursesGetterTest() {
        assertEquals(passedCourses, student.getPassedCourses());
    }

    @Test
    void passedCoursesSetterTest() {
        student.setPassedCourses(new HashMap<>());
        assertEquals(new HashMap<>(), student.getPassedCourses());
    }

    @Test
    void candidateCoursesGetterTest() {
        assertEquals(candidateCourses, student.getCandidateCourses());
    }

    @Test
    void candidateCoursesSetterTest() {
        student.setCandidateCourses(new HashSet<>());
        assertEquals(new HashSet<>(), student.getCandidateCourses());
    }

    @Test
    void taCoursesGetterTest() {
        assertEquals(taCourses, student.getTaCourses());
    }

    @Test
    void taCoursesSetterTest() {
        student.setTaCourses(new HashSet<>());
        assertEquals(new HashSet<>(), student.getTaCourses());
    }

    @Test
    void equalsTest() {
        Map<String, Float> equalPassedCourses = new HashMap<>();
        equalPassedCourses.put("CSE2115", 10.0f);
        equalPassedCourses.put("CSE1400", 10.0f);
        equalPassedCourses.put("CSE1105", 10.0f);
        Student equalStudent = new Student(netId, name);
        Set<String> equalCandidateCourses = new HashSet<>();
        equalCandidateCourses.add("CSE2115-2022");
        equalCandidateCourses.add("CSE1400-2021");
        Set<String> equalTaCourses = new HashSet<>();
        equalTaCourses.add("CSE1105-2022");
        equalStudent.setPassedCourses(equalPassedCourses);
        equalStudent.setCandidateCourses(equalCandidateCourses);
        equalStudent.setTaCourses(equalTaCourses);

        assertEquals(equalStudent, student);
    }

    @Test
    void equalsFalseTest() {
        assertNotEquals(new Student("ohageman", "Oisín"), student);
    }

    @Test
    void equalsSameTest() {
        Student sameStudent = student;
        assertEquals(sameStudent, student);
    }

    @Test
    void hashCodeTest() {
        Map<String, Float> equalPassedCourses = new HashMap<>();
        equalPassedCourses.put("CSE2115", 10.0f);
        equalPassedCourses.put("CSE1400", 10.0f);
        equalPassedCourses.put("CSE1105", 10.0f);
        Student equalStudent = new Student(netId, name);
        Set<String> equalCandidateCourses = new HashSet<>();
        equalCandidateCourses.add("CSE2115-2022");
        equalCandidateCourses.add("CSE1400-2021");
        Set<String> equalTaCourses = new HashSet<>();
        equalTaCourses.add("CSE1105-2022");
        equalStudent.setPassedCourses(equalPassedCourses);
        equalStudent.setCandidateCourses(equalCandidateCourses);
        equalStudent.setTaCourses(equalTaCourses);

        assertEquals(equalStudent.hashCode(), student.hashCode());
    }

    @Test
    void hashCodeFalseTest() {
        assertNotEquals(new Student("ohageman", "Oisín").hashCode(), student.hashCode());
    }
}
