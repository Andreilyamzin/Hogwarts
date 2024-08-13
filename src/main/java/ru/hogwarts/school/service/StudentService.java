package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.RecordNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;


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

    public Collection<String> getNameStartsWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .toList();
    }

    public double getAverageAge() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0);
    }

    public void printParallel() {
        var students = studentRepository.findAll();
        logger.info(students.get(0).toString());
        logger.info(students.get(1).toString());

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logger.info(students.get(2).toString());
            logger.info(students.get(3).toString());
        }).start();

        new Thread(() -> {
            logger.info(students.get(4).toString());
            logger.info(students.get(5).toString());
        }).start();

    }

    public void printSync() {
        var students = studentRepository.findAll();
        print(students.get(0));
        print(students.get(1));

        new Thread(() -> {
            print(students.get(2));
            print(students.get(3));
        }).start();

        new Thread(() -> {
            print(students.get(4));
            print(students.get(5));
        }).start();

    }

    private synchronized void print(Object o) {
        logger.info(o.toString());
    }


}
