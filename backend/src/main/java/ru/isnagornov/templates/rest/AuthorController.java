package ru.isnagornov.templates.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.isnagornov.templates.entity.Author;
import ru.isnagornov.templates.form.AuthorForm;
import ru.isnagornov.templates.form.converter.AuthorDtoConverter;
import ru.isnagornov.templates.repository.AuthorRepository;
import ru.isnagornov.templates.service.AuthorService;
import ru.isnagornov.templates.service.impl.AuthorServiceImpl;

@Controller
@RequestMapping("authors")
public class AuthorController extends AbstractController<Long, Author, AuthorForm, AuthorRepository, AuthorService,
        AuthorDtoConverter> {

    @Autowired
    protected AuthorController(AuthorService service, AuthorDtoConverter converter) {
        super(service, converter, AuthorForm.class);
    }
}
