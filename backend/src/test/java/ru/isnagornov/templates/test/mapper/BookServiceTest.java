package ru.isnagornov.templates.test.mapper;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ru.isnagornov.templates.Application;
import ru.isnagornov.templates.entity.Author;
import ru.isnagornov.templates.entity.Book;
import ru.isnagornov.templates.service.BookService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookServiceTest {

    private static final Long ID = 1L;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private BeanPropertyRowMapper<Book> rowMapper = new BeanPropertyRowMapper<>(Book.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BookService service;

    @Test
    public void test1Insert() {
        Book book = new Book();
        book.setName("TestEntity");

        service.add(book);
        assertNotNull(book.getId());

        logger.info("{} added to repository", book);
    }

    @Test
    public void test2Get() {
        Book foundBook = getEntityByIdUsingJdbcTemplate(ID);

        assertNotNull(foundBook);

        Book book = service.getById(ID);

        logger.info("{} was gotten from repository by ID={}", book, ID);

        assertEquals(book, foundBook);
    }

    @Test
    public void test4Update() {
        Book preUpdatedBook = getEntityByIdUsingJdbcTemplate(ID);

        preUpdatedBook.setName("updated!!!");

        service.update(preUpdatedBook);

        Book updatedBook = getEntityByIdUsingJdbcTemplate(ID);

        assertEquals(preUpdatedBook, updatedBook);

        logger.info("Book was updated in repository, new value - {}", updatedBook);
    }

    @Test
    public void test5Delete() {

        Book foundBook = getEntityByIdUsingJdbcTemplate(ID);

        assertNotNull(foundBook);

        service.delete(ID);

        List<Book> foundList = jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{ID},
                rowMapper);
        assertEquals(0, foundList.size());

        logger.info("Book with ID={} was deleted from repository", ID);
    }

    private Book getEntityByIdUsingJdbcTemplate(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id=?", new Object[]{id},
                rowMapper);
    }
}
