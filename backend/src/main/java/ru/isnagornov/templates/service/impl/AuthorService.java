package ru.isnagornov.templates.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.isnagornov.templates.entity.Author;
import ru.isnagornov.templates.entity.Book;
import ru.isnagornov.templates.repository.AuthorRepository;
import ru.isnagornov.templates.service.AbstractService;

@Service
public class AuthorService extends AbstractService<Long, Author, AuthorRepository> {

    public AuthorService(AuthorRepository repository) {
        super(repository);
    }
}
