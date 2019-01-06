package ru.isnagornov.templates.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isnagornov.templates.entity.Entity;
import ru.isnagornov.templates.service.AbstractService;

import java.util.List;

@RestController
@RequestMapping(path = "entity")
public class EntityCrudRestController extends AbstractController<Entity> {

    public EntityCrudRestController(AbstractService<Entity> service) {
        super(service);
    }

    @Override
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody Entity entity) {
        return super.add(entity);
    }

    @Override
    @GetMapping("/getAll")
    public List<Entity> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(path = "/{id}")
    public Entity getById(@PathVariable("id") Long id) {
        return super.getById(id);
    }

    @Override
    @PutMapping(path = "/update")
    public ResponseEntity<HttpStatus> update(@RequestBody Entity entity) {
        return super.update(entity);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        return super.delete(id);
    }

}
