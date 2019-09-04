package ru.isnagornov.templates.service;

import java.util.List;

public interface CrudService<ID, E> {

    void add(E entity);

    E getById(ID id);

    List<E> getAll();

    void update(E entity);

    void delete(ID id);

}
