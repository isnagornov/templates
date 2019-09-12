package ru.isnagornov.templates.service;

import ru.isnagornov.templates.entity.BookComment;

import java.util.List;

public interface BookCommentService extends CrudService<Long, BookComment> {

    List<BookComment> getAllByBook(Long bookId);

    long countByBookId(Long bookId);
}
