package ru.isnagornov.templates.form.converter;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.isnagornov.templates.entity.Book;
import ru.isnagornov.templates.form.BookForm;
import ru.isnagornov.templates.service.impl.BookCommentService;

@Component
public class BookDtoConverter extends BaseConverter<Book, BookForm> {

    @Autowired
    private BookCommentService bookCommentService;

    public BookDtoConverter() {
        super(Book.class, BookForm.class);

        getClassMapBuilder().customize(new CustomMapper<Book, BookForm>() {

            @Override
            public void mapAtoB(Book book, BookForm bookForm, MappingContext context) {
                super.mapAtoB(book, bookForm, context);
                bookForm.setCommentsNumber(bookCommentService.countByBookId(book.getId()));
            }
        }).byDefault().register();
    }
}
