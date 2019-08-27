package ru.isnagornov.templates.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.isnagornov.templates.entity.Book;

@Service
public class BookService extends AbstractService<Long, Book> {

    public BookService(JpaRepository<Book, Long> repository) {
        super(repository);
    }
}
