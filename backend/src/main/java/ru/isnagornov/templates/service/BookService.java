package ru.isnagornov.templates.service;

import org.springframework.stereotype.Service;
import ru.isnagornov.templates.entity.Book;
import ru.isnagornov.templates.repository.BookRepository;

@Service
public class BookService extends AbstractService<Long, Book, BookRepository> {

    public BookService(BookRepository repository) {
        super(repository);
    }
}
