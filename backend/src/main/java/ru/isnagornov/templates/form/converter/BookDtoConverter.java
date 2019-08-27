package ru.isnagornov.templates.form.converter;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.isnagornov.templates.entity.Book;
import ru.isnagornov.templates.form.BookForm;

@Component
public class BookDtoConverter extends BaseConverter<Book, BookForm> {

    @Autowired
    private AuthorDtoConverter authorDtoConverter;

    public BookDtoConverter() {
        super(Book.class, BookForm.class);

        getClassMapBuilder().customize(new CustomMapper<Book, BookForm>() {

            @Override
            public void mapAtoB(Book book, BookForm bookForm, MappingContext context) {
                super.mapAtoB(book, bookForm, context);
                book.setAuthor(authorDtoConverter.getEntity(bookForm.getAuthor()));
            }

            @Override
            public void mapBtoA(BookForm bookForm, Book book, MappingContext context) {
                super.mapBtoA(bookForm, book, context);
                bookForm.setAuthor(authorDtoConverter.getDto(book.getAuthor()));
            }
        }).byDefault().register();
    }
}
