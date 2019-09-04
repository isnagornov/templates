package ru.isnagornov.templates.test.service


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.isnagornov.templates.Application
import ru.isnagornov.templates.entity.Author
import ru.isnagornov.templates.entity.Book
import ru.isnagornov.templates.service.impl.BookCommentService
import ru.isnagornov.templates.service.impl.BookService
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
    private BookCommentService bookCommentService

    @Autowired
    private DbUtil dbUtil

    void testInsert() {
        given:
        Book entityToInsert = [name: "TestBook1", link: "link1", author: [id: 1L] as Author] as Book

        when:
        bookService.add(entityToInsert)

        then:
        entityToInsert.id != null

        logger.info("{} added to repository", entityToInsert)

        dbUtil.deleteEntityById(tableName, entityToInsert.id)
    }

    void testGet() {
        given:
        final Long id = 1L
        Book found = dbUtil.getEntityById(id, tableName, bookMapper)

        when:
        Book entity = bookService.getById(id)

        then:
        verifyAll(entity) {
            it.id == found.id
            it.name == found.name
            it.link == found.link
            verifyAll(author) {
                id == found.author.id
                name == found.author.name
                biography == found.author.biography
            }
        }

        logger.info("{} was gotten from repository by id={}", entity, id)
    }

    void testUpdate() {
        given:
        final Long id = 1L
        Book toUpdate = dbUtil.getEntityById(id, tableName, bookMapper)

        toUpdate.properties.each {
            if (it.value instanceof String) {
                toUpdate.metaClass.setAttribute(toUpdate, it.key, it.value + "Updated!")
            }
        }

        when:
        bookService.update(toUpdate)

        then:
        Book afterUpdate = dbUtil.getEntityById(id, tableName, bookMapper)

        with(afterUpdate) {
            properties.each {
                if (it.value instanceof String) {
                    assert (it.value as String).contains("Updated!")
                }
            }
        }

        logger.info("Entity was updated in repository, new value - {}", afterUpdate)
    }

    void testDelete() {
        given:
        final Long idToDelete = dbUtil.insertBook([name: "TestBookToDelete", link: "link1", author: [id: 1L] as Author] as Book)

        when:
        bookService.delete(idToDelete)

        then:
        Book found = dbUtil.getEntityById(idToDelete, tableName, bookMapper)
        found == null

        logger.info("Entity with id={} was deleted from repository", idToDelete)
    }


}
