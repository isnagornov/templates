package ru.isnagornov.templates.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractService<T, E, R extends JpaRepository<E, T>> {

    protected R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Transactional
    public void add(E entity) {
        this.repository.save(entity);
    }

    @Transactional(readOnly = true)
    public E getById(T id) {
        return this.repository.findById(id).orElse(null);
    }

    public List<E> getAll() {
        return this.repository.findAll();
    }

    @Transactional
    public void update(E entity) {
        this.repository.save(entity);
    }

    @Transactional
    public void delete(T id) {
        this.repository.deleteById(id);
    }
}
