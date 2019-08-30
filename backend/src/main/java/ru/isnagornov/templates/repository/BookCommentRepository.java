package ru.isnagornov.templates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isnagornov.templates.entity.BookComment;

import java.util.List;

@Repository
public interface BookCommentRepository extends JpaRepository<BookComment, Long> {

    List<BookComment> findByBookId(Long bookId);
}
