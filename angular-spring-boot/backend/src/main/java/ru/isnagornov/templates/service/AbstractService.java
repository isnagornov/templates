package ru.isnagornov.templates.service;

import ru.isnagornov.templates.mapper.CommonMapper;

import java.util.List;

public abstract class AbstractService<T, M extends CommonMapper<T>> {

    protected M mapper;

    public AbstractService(M mapper) {
        this.mapper = mapper;
    }

    public void add(T entity) {
        this.mapper.add(entity);
    }

    public T getById(Long id) {
        return this.mapper.getById(id);
    }

    public List<T> getAll() {
        return this.mapper.getAll();
    }

    public void update(T entity) {
        this.mapper.update(entity);
    }

    public void delete(Long id) {
        this.mapper.delete(id);
    }
}
