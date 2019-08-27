package ru.isnagornov.templates.form.converter;

import org.springframework.stereotype.Component;
import ru.isnagornov.templates.entity.Author;
import ru.isnagornov.templates.form.AuthorForm;
import ru.isnagornov.templates.form.converter.BaseConverter;

@Component
public class AuthorDtoConverter extends BaseConverter<Author, AuthorForm> {

    public AuthorDtoConverter() {
        super(Author.class, AuthorForm.class);
    }
}
