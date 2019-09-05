package ru.isnagornov.templates.test.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.isnagornov.templates.Application
import ru.isnagornov.templates.entity.Author
import ru.isnagornov.templates.service.AuthorService
import ru.isnagornov.templates.service.impl.AuthorServiceImpl
import ru.isnagornov.templates.test.util.DbUtil
import spock.lang.Specification

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
// all test data stored in csv files at \templates\backend\src\main\resources\liquibase\data
class AuthorServiceTest extends Specification {

    private Logger logger = LoggerFactory.getLogger(this.getClass())
    private static final String tableName = "author"

    @Autowired
    private AuthorService authorService

    @Autowired
    private DbUtil dbUtil

    void testInsert() {
        given:
        Author entityToInsert = [name: "TestAuthor1", biography: "!biography!"] as Author

        when:
        authorService.add(entityToInsert)

        then:
        entityToInsert.id != null

        logger.info("{} added to repository", entityToInsert)

        dbUtil.deleteEntityById(tableName, entityToInsert.id)
    }

    void testGet() {
        given:
        final Long id = 1L
        Author found = dbUtil.getEntityById(id, tableName, DbUtil.authorRowMapper)

        when:
        Author entity = authorService.getById(id)

        then:
        verifyAll(entity) {
            it.id == found.id
            it.name == found.name
            it.biography == found.biography
        }

        logger.info("{} was gotten from repository by id={}", entity, id)
    }

    void testUpdate() {
        given:
        final Long id = 1L
        Author toUpdate = dbUtil.getEntityById(id, tableName, DbUtil.authorRowMapper)

        toUpdate.properties.each {
            if (it.value instanceof String) {
                toUpdate.metaClass.setAttribute(toUpdate, it.key, it.value + "Updated!")
            }
        }

        when:
        authorService.update(toUpdate)

        then:
        Author afterUpdate = dbUtil.getEntityById(id, tableName, DbUtil.authorRowMapper)

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
        final Long idToDelete = dbUtil.insertAuthor([name: "TestAuthorToDelete", biography: "!biography!"] as Author)

        when:
        authorService.delete(idToDelete)

        then:
        Author found = dbUtil.getEntityById(idToDelete, tableName, DbUtil.authorRowMapper)
        found == null

        logger.info("Entity with id={} was deleted from repository", idToDelete)
    }

    void testFindByName() {
        given:
        final String name = 'AUTHOR'

        when:
        List<Author> list = authorService.findByNameContainsIgnoreCase(name)

        then:
        !list.isEmpty()
        list.each {
            assert it.name.toLowerCase().contains(name.toLowerCase())
        }

        logger.info("{} was found in repository by name={}", list, name)
    }


}
