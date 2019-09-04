package ru.isnagornov.templates.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractService<ID, E, R extends JpaRepository<E, ID>> implements CrudService<ID, E> {

    protected R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void add(E entity) {
        this.repository.save(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public E getById(ID id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public List<E> getAll() {
        return this.repository.findAll();
    }

    @Transactional
    @Override
    public void update(E entity) {
        this.repository.save(entity);
    }

    @Transactional
    @Override
    public void delete(ID id) {
        this.repository.deleteById(id);
    }
}
