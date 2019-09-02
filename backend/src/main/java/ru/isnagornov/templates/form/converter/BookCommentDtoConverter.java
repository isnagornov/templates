package ru.isnagornov.templates.form.converter;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.isnagornov.templates.entity.BookComment;
import ru.isnagornov.templates.form.BookCommentForm;

@Component
public class BookCommentDtoConverter extends BaseConverter<BookComment, BookCommentForm> {

    @Autowired
    private BookDtoConverter bookDtoConverter;

    public BookCommentDtoConverter() {
        super(BookComment.class, BookCommentForm.class);

        getClassMapBuilder().customize(new CustomMapper<BookComment, BookCommentForm>() {
            @Override
            public void mapAtoB(BookComment bookComment, BookCommentForm bookCommentForm, MappingContext context) {
                super.mapAtoB(bookComment, bookCommentForm, context);
                bookComment.setBook(bookDtoConverter.getEntity(bookCommentForm.getBook()));
            }

            @Override
            public void mapBtoA(BookCommentForm bookCommentForm, BookComment bookComment, MappingContext context) {
                super.mapBtoA(bookCommentForm, bookComment, context);
                bookCommentForm.setBook(bookDtoConverter.getDto(bookComment.getBook()));

            }

        }).byDefault().register();
    }
}
