package ru.isnagornov.templates.service

import ru.isnagornov.templates.mapper.CommonMapper

abstract class AbstractService<T, M extends CommonMapper<T>> {

    protected M mapper

    AbstractService(M mapper) {
        this.mapper = mapper
    }

    void add(T entity) {
        this.mapper.add(entity)
    }

    T getById(Long id) {
        return this.mapper.getById(id)
    }

    List<T> getAll() {
        return this.mapper.getAll()
    }

    void update(T entity) {
        this.mapper.update(entity)
    }

    void delete(Long id) {
        this.mapper.delete(id)
    }
}
