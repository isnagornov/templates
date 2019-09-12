package ru.isnagornov.templates.test.service


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.isnagornov.templates.Application
import ru.isnagornov.templates.entity.Book
import ru.isnagornov.templates.entity.BookComment
import ru.isnagornov.templates.service.BookCommentService
import ru.isnagornov.templates.service.impl.BookCommentServiceImpl
import ru.isnagornov.templates.test.mappers.BookCommentMapper
import ru.isnagornov.templates.test.util.DbUtil
import spock.lang.Specification

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
// all test data stored in csv files at \templates\backend\src\main\resources\liquibase\data
class BookCommentServiceTest extends Specification {

    private Logger logger = LoggerFactory.getLogger(this.getClass())
    private static final String tableName = "book_comment"

    @Autowired
    private BookCommentMapper bookCommentMapper

    @Autowired
    private BookCommentService bookCommentService

    @Autowired
    private DbUtil dbUtil

    void testInsert() {
        given:
        BookComment entityToInsert = [comment: "comment!", rate: 5, user: "user1", book: [id: 1L] as Book] as BookComment

        when:
        bookCommentService.add(entityToInsert)

        then:
        entityToInsert.id != null

        logger.info("{} added to repository", entityToInsert)

        dbUtil.deleteEntityById(tableName, entityToInsert.id)
    }

    void testGet() {
        given:
        final Long id = 1L
        BookComment found = dbUtil.getEntityById(id, tableName, bookCommentMapper)

        when:
        BookComment entity = bookCommentService.getById(id)

        then:
        verifyAll(entity) {
            it.id == found.id
            it.comment == found.comment
            it.rate == found.rate
            it.user == found.user
            verifyAll(book) {
                id == found.book.id
                name == found.book.name
                link == found.book.link
            }
        }

        logger.info("{} was gotten from repository by id={}", entity, id)
    }

    void testUpdate() {
        given:
        final Long id = 1L
        BookComment toUpdate = dbUtil.getEntityById(id, tableName, bookCommentMapper)

        toUpdate.properties.each {
            if (it.value instanceof String) {
                toUpdate.metaClass.setAttribute(toUpdate, it.key, it.value + "Updated!")
            }
        }

        when:
        bookCommentService.update(toUpdate)

        then:
        BookComment bookToAfterUpdate = dbUtil.getEntityById(id, tableName, bookCommentMapper)

        with(bookToAfterUpdate) {
            properties.each {
                if (it.value instanceof String) {
                    assert (it.value as String).contains("Updated!")
                }
            }
        }

        logger.info("Entity was updated in repository, new value - {}", bookToAfterUpdate)
    }

    void testDelete() {

        given:
        final Long idToDelete = dbUtil.insertBookComment(
                [comment: "comment!", rate: 5, user: "user1", book: [id: 1L] as Book] as BookComment
        )

        when:
        bookCommentService.delete(idToDelete)

        then:
        BookComment found = dbUtil.getEntityById(idToDelete, tableName, bookCommentMapper)
        found == null

        logger.info("Entity with id={} was deleted from repository", idToDelete)
    }

    void testFindByBook() {

        given:
        final Long id = 1L

        when:
        List<BookComment> bookComments = bookCommentService.getAllByBook(id)

        then:
        !bookComments.isEmpty()
        bookComments.each {assert it.book.id == id}
        bookComments*.id == [1L, 3L]

        logger.info("{} with book_id={} was found in repository", bookComments, id)
    }

}
