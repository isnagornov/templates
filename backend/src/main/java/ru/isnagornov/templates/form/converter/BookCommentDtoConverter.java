package ru.isnagornov.templates.form.converter;

import org.springframework.stereotype.Component;
import ru.isnagornov.templates.entity.BookComment;
import ru.isnagornov.templates.form.BookCommentForm;

@Component
public class BookCommentDtoConverter extends BaseConverter<BookComment, BookCommentForm> {

    public BookCommentDtoConverter() {
        super(BookComment.class, BookCommentForm.class);
    }
}
