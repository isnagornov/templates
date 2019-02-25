package ru.isnagornov.templates.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isnagornov.templates.entity.AbstractEntity;

import java.util.List;

public abstract class AbstractService<T, E extends AbstractEntity<T>> {

    protected JpaRepository<E, T> repository;

    public AbstractService(JpaRepository<E, T> repository) {
        this.repository = repository;
    }

    public void add(E entity) {
        this.repository.save(entity);
    }

    public E getById(T id) {
        return this.repository.findById(id).orElse(null);
    }

    public List<E> getAll() {
        return this.repository.findAll();
    }

    public void update(E entity) {
        this.repository.save(entity);
    }

    public void delete(T id) {
        this.repository.deleteById(id);
    }
}
