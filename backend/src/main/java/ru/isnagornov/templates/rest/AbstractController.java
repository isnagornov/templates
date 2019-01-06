package ru.isnagornov.templates.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.isnagornov.templates.service.AbstractService;

import java.util.List;

public abstract class AbstractController<T> {

    private AbstractService<T> service;

    public AbstractController(AbstractService<T> service) {
        this.service = service;
    }

    public ResponseEntity<HttpStatus> add(T entity) {
        this.service.add(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public T getById(Long id) {
        return this.service.getById(id);
    }

    public List<T> getAll() {
        return this.service.getAll();
    }

    public ResponseEntity<HttpStatus> update(T entity) {
        this.service.update(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> delete(Long id) {
        this.service.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
