package ru.isnagornov.templates.mapper;


import java.util.List;

public interface CommonMapper<T> {

    void add(T entity);

    T getById(Long id);

    List<T> getAll();

    void update(T entity);

    void delete(Long id);
}
