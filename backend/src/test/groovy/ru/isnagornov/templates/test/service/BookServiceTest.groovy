package ru.isnagornov.templates.test.service


import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.isnagornov.templates.Application
import ru.isnagornov.templates.entity.Author
import ru.isnagornov.templates.entity.Book
import ru.isnagornov.templates.service.BookService
import ru.isnagornov.templates.test.mappers.BookMapper
import ru.isnagornov.templates.test.util.DbUtil
import spock.lang.Specification

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
// all test data stored in csv files at \templates\backend\src\main\resources\liquibase\data
class BookServiceTest extends Specification {

    private Logger logger = LoggerFactory.getLogger(this.getClass())
    private static final String tableName = "book"

    @Autowired
    private BookService bookService

    @Autowired
    private BookMapper bookMapper

    @Autowired
    private DbUtil dbUtil

    @Test
    void test1Insert() {
        given:
        Book entityToInsert = [name: "TestBook1", link: "link1", rate: 5, author: [id: 1L] as Author] as Book

        when:
        bookService.add(entityToInsert)

        then:
        entityToInsert.id != null

        logger.info("{} added to repository", entityToInsert)

        dbUtil.deleteEntityById(tableName, entityToInsert.id)
    }

    @Test
    void test2Get() {
        given:
        final Long id = 1L
        Book foundBook = dbUtil.getEntityById(id, "book", bookMapper)

        when:
        Book entity = bookService.getById(id)

        then:
        verifyAll(entity) {
            it.id == foundBook.id
            it.name == foundBook.name
            it.rate == foundBook.rate
            it.link == foundBook.link
            verifyAll(author) {
                id == foundBook.author.id
                name == foundBook.author.name
                biography == foundBook.author.biography
            }
        }

        logger.info("{} was gotten from repository by id={}", entity, id)
    }

    @Test
    void test4Update() {
        given:
        final Long id = 1L
        Book bookToUpdate = dbUtil.getEntityById(id, "book", bookMapper)

        bookToUpdate.properties.each {
            if (it.value instanceof String) {
                bookToUpdate.metaClass.setAttribute(bookToUpdate, it.key, it.value + "Updated!")
            }
        }
        println bookToUpdate

        when:
        bookService.update(bookToUpdate)

        then:
        Book bookToAfterUpdate = dbUtil.getEntityById(id, "book", bookMapper)

        with(bookToAfterUpdate) {
            properties.each {
                if (it.value instanceof String) {
                    assert (it.value as String).contains("Updated!")
                }
            }
        }

        logger.info("Book was updated in repository, new value - {}", bookToAfterUpdate)
    }

    @Test
    void test5Delete() {
        given:
        final Long idToDelete = dbUtil.insertBook([name: "TestBookToDelete", link: "link1", rate: 5, author: [id: 1L] as Author] as Book)

        when:
        bookService.delete(idToDelete)

        then:
        Book found = dbUtil.getEntityById(idToDelete, tableName, bookMapper)
        found == null

        logger.info("Book with id={} was deleted from repository", idToDelete)
    }


}
