package ru.isnagornov.templates.service;

import ru.isnagornov.templates.entity.Book;

import java.util.List;

public interface BookService extends CrudService<Long, Book> {

    List<Book> findByAuthor(Long authorId);
}
