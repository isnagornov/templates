package ru.isnagornov.templates.service.impl;

import org.springframework.stereotype.Service;
import ru.isnagornov.templates.entity.Book;
import ru.isnagornov.templates.repository.BookRepository;
import ru.isnagornov.templates.service.AbstractService;
import ru.isnagornov.templates.service.BookService;

@Service
public class BookServiceImpl extends AbstractService<Long, Book, BookRepository> implements BookService {

    public BookServiceImpl(BookRepository repository) {
        super(repository);
    }
}
