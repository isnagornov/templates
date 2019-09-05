package ru.isnagornov.templates.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isnagornov.templates.entity.Author;
import ru.isnagornov.templates.repository.AuthorRepository;
import ru.isnagornov.templates.service.AbstractService;
import ru.isnagornov.templates.service.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl extends AbstractService<Long, Author, AuthorRepository> implements AuthorService {

    public AuthorServiceImpl(AuthorRepository repository) {
        super(repository);
    }

    @Transactional(readOnly = true)
    public List<Author> findByNameContainsIgnoreCase(String name) {

        return repository.findByNameContainsIgnoreCase(name);
    }
}
