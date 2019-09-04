package ru.isnagornov.templates.service.impl;

import org.springframework.stereotype.Service;
import ru.isnagornov.templates.entity.Book;
import ru.isnagornov.templates.repository.BookRepository;
import ru.isnagornov.templates.service.AbstractService;

@Service
public class BookService extends AbstractService<Long, Book, BookRepository> {

    public BookService(BookRepository repository) {
        super(repository);
    }
}
