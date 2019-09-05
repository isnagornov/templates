package ru.isnagornov.templates.service.impl;

import org.springframework.stereotype.Service;
import ru.isnagornov.templates.entity.BookComment;
import ru.isnagornov.templates.repository.BookCommentRepository;
import ru.isnagornov.templates.service.AbstractService;
import ru.isnagornov.templates.service.BookCommentService;

import java.util.List;

@Service
public class BookCommentServiceImpl extends AbstractService<Long, BookComment, BookCommentRepository>
        implements BookCommentService {

    public BookCommentServiceImpl(BookCommentRepository repository) {
        super(repository);
    }

    public List<BookComment> getAllByBook(Long bookId) {
        return repository.findByBookId(bookId);
    }

    public long countByBookId(Long bookId) {
        return repository.countByBookId(bookId);
    }
}
