package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.RecordNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;


@Service
public class FacultyService {
    private final static Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        logger.info("FacultyService created");
        this.repository = repository;
    }


    public Faculty add(Faculty faculty) {
        logger.info("Faculty add");
        return repository.save(faculty);
    }

    public Faculty get(long id) {
        logger.info("Faculty get");
        return repository.findById(id).orElseThrow(RecordNotFoundException::new);

    }

    public boolean delete(long id) {
        logger.info("Faculty delete");
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return true;
                }).orElse(false);
    }

    public Faculty update(Faculty faculty) {
        logger.info("Faculty update");
        return repository.findById(faculty.getId())
                .map(entity -> {
                    return repository.save(faculty);
                })
                .orElse(null);
    }

    public Collection<Faculty> getByColorAndName(String color, String name) {
        logger.info("There is no such faculty");
        return repository.findAllByColorIgnoreCaseOrAndNameIgnoreCase(color, name);
    }

    public Collection<Faculty> getAll() {
        logger.info("There is no such faculty");
        return repository.findAll();
    }

    public String getLongestName(){
        return repository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse("");
    }
}
