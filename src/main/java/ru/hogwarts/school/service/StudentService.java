package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.RecordNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;


@Service
public class StudentService {
    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository repository;
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }


    public Student add(Student student) {
        logger.info("add student {}", student);
        return repository.save(student);
    }

    public Student get(long id) {
        logger.info("get student {}", id);
        return repository.findById(id).orElseThrow(RecordNotFoundException::new);


    }

    public boolean delete(long id) {
        logger.info("delete student {}", id);
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return true;
                }).orElse(false);
    }

    public Student update(Student student) {
        logger.info("update student {}", student);
        return repository.findById(student.getId())
                .map(entity -> {
                    return repository.save(student);
                })
                .orElse(null);
    }

    public Collection<Student> getByAgeBetween(int min, int max) {
        logger.info("get student by age between {} and {}", min, max);
        return repository.findAllByAgeBetween(min, max);
    }

    public Collection<Student> getAll() {
        logger.info("get all students");
        return repository.findAll();
    }

    public int getStudentCount() {
        logger.info("get student count");
        return studentRepository.countStudents();
    }
    public double getAvgAge() {
        logger.info("get student avg age");
        return studentRepository.avgAge();
    }

    public Collection<Student> getLastFive() {
        logger.info("get student last five students");
        return studentRepository.getLastFive();
    }
}
