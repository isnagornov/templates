package ru.isnagornov.templates.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isnagornov.templates.mapper.CommonMapper;
import ru.isnagornov.templates.service.AbstractService;

import java.util.List;

public abstract class AbstractController<T, M extends CommonMapper<T>, S extends AbstractService<T, M>> {

    protected S service;

    public AbstractController(S service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody T entity) {
        this.service.add(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public T getById(@PathVariable("id") Long id) {
        return this.service.getById(id);
    }

    @GetMapping("/getAll")
    public List<T> getAll() {
        return this.service.getAll();
    }


    @PutMapping(path = "/update")
    public ResponseEntity<HttpStatus> update(@RequestBody T entity) {
        this.service.update(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        this.service.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
