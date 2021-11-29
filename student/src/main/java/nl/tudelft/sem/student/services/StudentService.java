package nl.tudelft.sem.student.services;

import nl.tudelft.sem.student.entities.Student;
import nl.tudelft.sem.student.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    private final transient StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<Student> getStudent(long id) {
        return studentRepository.findById(id);
    }
}
