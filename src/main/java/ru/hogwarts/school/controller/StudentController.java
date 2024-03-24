package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public Student get(@RequestParam Long id) {
        return service.get(id);
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return service.add(student);
    }

    @DeleteMapping
    public boolean delete(@RequestParam Long id) {
        return service.delete(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return service.update(student);
    }

    @GetMapping("/byAge")
    public Collection<Student> getByAge(@RequestParam int age) {
        return service.getByAge(age);
    }
}
