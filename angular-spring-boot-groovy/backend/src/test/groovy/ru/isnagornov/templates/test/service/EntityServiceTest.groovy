package ru.isnagornov.templates.test.service

import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import ru.isnagornov.templates.Application
import ru.isnagornov.templates.entity.Entity
import ru.isnagornov.templates.service.EntityService
import spock.lang.Specification

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class EntityServiceTest extends Specification {

    private Logger logger = LoggerFactory.getLogger(this.getClass())

    private BeanPropertyRowMapper<Entity> rowMapper = new BeanPropertyRowMapper<>(Entity.class)

    @Autowired
    private JdbcTemplate jdbcTemplate

    @Autowired
    private EntityService entityService

    @Test
    void test1Insert() {
        given:
        final Long id = 1L
        Entity entity = [name: "TestEntity"] as Entity

        when:
        entityService.add(entity)

        then:
        Entity foundEntity = getEntityByIdUsingJdbcTemplate(id)

        assert entity.id != null
        with(foundEntity) {
            entity == it
        }

        logger.info("{} added to repository", entity)

    }

    @Test
    void test2Get() {
        given:
        final Long id = 1L
        Entity entityInDb = getEntityByIdUsingJdbcTemplate(id)
        assert entityInDb != null

        expect:
        Entity foundEntity = entityService.getById(id)
        entityInDb == foundEntity

        logger.info("{} was gotten from repository by id={}", foundEntity, id)
    }

    @Test
    void test3GetAll() {
        given:
        List<Entity> expectedResultList = [new Entity(1L, "TestEntity")]

        expect:
        expectedResultList == entityService.getAll()

        logger.info("Entity list {} was gotten from repository", expectedResultList)
    }

    @Test
    void test4Update() {
        given:
        final Long id = 1L
        Entity entityInDb = getEntityByIdUsingJdbcTemplate(id)
        Entity entity = [id, "TestEntityUpdated!"] as Entity

        assert entityInDb != entity

        when:
        entityService.update(entity)

        then:
        entity == getEntityByIdUsingJdbcTemplate(id)

        logger.info("Entity was updated in repository, new value - {}", entity)
    }

    @Test
    void test5Delete() {
        given:
        Long id = 1L
        Entity entityInDb = getEntityByIdUsingJdbcTemplate(id)

        assert entityInDb != null

        when:
        entityService.delete(id)

        then:
        List<Entity> foundList = jdbcTemplate.query(String.format("SELECT * FROM entity WHERE id=%s", id),
                rowMapper)

        foundList.size() == 0

        logger.info("Entity with id={} was deleted from repository", id)
    }

    private Entity getEntityByIdUsingJdbcTemplate(Long id) {
        return jdbcTemplate.queryForObject(String.format("SELECT * FROM entity WHERE id=%s", id), rowMapper)
    }
}
