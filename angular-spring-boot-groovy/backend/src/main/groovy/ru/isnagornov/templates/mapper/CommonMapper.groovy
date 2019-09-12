package ru.isnagornov.templates.mapper

interface CommonMapper<T> {
    void add(T entity)

    T getById(Long id)

    List<T> getAll()

    void update(T entity)

    void delete(Long id)
}