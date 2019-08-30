package ru.isnagornov.templates.service;

import org.springframework.stereotype.Service;
import ru.isnagornov.templates.entity.BookComment;
import ru.isnagornov.templates.repository.BookCommentRepository;

import java.util.List;

@Service
public class BookCommentService extends AbstractService<Long, BookComment, BookCommentRepository> {

    public BookCommentService(BookCommentRepository repository) {
        super(repository);
    }

    public List<BookComment> getAllByBook(Long bookId) {
        return repository.findByBookId(bookId);
    }
}
