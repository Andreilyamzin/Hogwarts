package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService service;
    private final FacultyService facultyService;

    public FacultyController(FacultyService service, FacultyService facultyService) {
        this.service = service;
        this.facultyService = facultyService;
    }

    @GetMapping
    public Faculty get(@RequestParam Long id) {
        return service.get(id);
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return service.add(faculty);
    }

    @DeleteMapping
    public boolean delete(@RequestParam Long id) {
        return service.delete(id);
    }

    @PutMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return service.update(faculty);
    }

    @GetMapping("byColorAndName")
    public Collection<Faculty> getByColor(@RequestParam(required = false) String color,
                                          @RequestParam(required = false) String name) {
        if (color == null && name == null) {
            return service.getAll();
        }

        return service.getByColorAndName(color, name);

    }

    @GetMapping("students")
    public List<Student> getStudentFaculty(@RequestParam long facultyId) {
        return service.get(facultyId).getStudents();
    }

    @GetMapping("/longestName")
    public String getLongestName() {
        return facultyService.getLongestName();
    }
}
