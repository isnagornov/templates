package ru.isnagornov.templates.service;

import ru.isnagornov.templates.entity.Author;

import java.util.List;

public interface AuthorService extends CrudService<Long, Author> {

    List<Author> findByNameContainsIgnoreCase(String name);
}
