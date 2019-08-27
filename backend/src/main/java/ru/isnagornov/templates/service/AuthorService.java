package ru.isnagornov.templates.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.isnagornov.templates.entity.Author;
import ru.isnagornov.templates.entity.Book;

@Service
public class AuthorService extends AbstractService<Long, Author> {

    public AuthorService(JpaRepository<Author, Long> repository) {
        super(repository);
    }
}
