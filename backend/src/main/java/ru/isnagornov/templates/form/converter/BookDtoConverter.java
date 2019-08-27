package ru.isnagornov.templates.form.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.isnagornov.templates.entity.Author;
import ru.isnagornov.templates.entity.Book;
import ru.isnagornov.templates.form.BookForm;
import ru.isnagornov.templates.service.AuthorService;

@Component
public class BookDtoConverter extends BaseConverter<Book, BookForm> {

    @Autowired
    private AuthorService authorService;

    public BookDtoConverter() {
        super(Book.class, BookForm.class);
    }

    @Override
    public Book getEntity(BookForm dto) {
        Book entity = super.getEntity(dto);

        entity.setAuthor(authorService.getById(dto.getAuthorId()));

        return entity;
    }

    @Override
    public BookForm getDto(Book entity) {
        BookForm dto = super.getDto(entity);

        Author author = entity.getAuthor();
        dto.setAuthorId(author.getId());
        dto.setAuthorName(author.getName());

        return dto;
    }
}
