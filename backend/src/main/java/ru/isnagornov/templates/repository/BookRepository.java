package ru.isnagornov.templates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isnagornov.templates.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
