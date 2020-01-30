package ru.isnagornov.templates.test.service

import org.junit.Assert.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.junit4.SpringRunner
import ru.isnagornov.templates.Application
import ru.isnagornov.templates.entity.Entity
import ru.isnagornov.templates.service.EntityService

@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class EntityServiceTest {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    private val rowMapper = BeanPropertyRowMapper(Entity::class.java)

    @Autowired
    private val jdbcTemplate: JdbcTemplate? = null

    @Autowired
    private val entityService: EntityService? = null

    @Test
    fun test1Insert() {
        val id = 1L
        val entity = Entity(name = "TestEntity")

        entityService!!.add(entity)
        assertEquals(id, entity.id)

        logger.info("{} added to repository", entity)

        val foundEntity = getEntityByIdUsingJdbcTemplate(id)

        assertEquals(entity, foundEntity)
    }

    @Test
    fun test2Get() {
        val id = 1L
        val foundEntity = getEntityByIdUsingJdbcTemplate(id)

        assertNotNull(foundEntity)

        val entity = entityService!!.getById(id)

        logger.info("{} was gotten from repository by id={}", entity, id)

        assertEquals(entity, foundEntity)
    }

    @Test
    fun test4Update() {
        var foundEntity = getEntityByIdUsingJdbcTemplate(1L)

        val entity = Entity(id = 1L, name = "TestEntity1")
        entity.id

        assertNotEquals(foundEntity, entity)

        entityService!!.update(entity)

        foundEntity = getEntityByIdUsingJdbcTemplate(1L)

        assertEquals(foundEntity, entity)

        logger.info("Entity was updated in repository, new value - {}", entity)
    }

    @Test
    fun test5Delete() {
        val id = 1L

        val foundEntity = getEntityByIdUsingJdbcTemplate(id)

        assertNotNull(foundEntity)

        entityService!!.delete(id)

        val foundList = jdbcTemplate!!.query("SELECT * FROM entity WHERE id=?", arrayOf<Any>(id),
                rowMapper)
        assertEquals(0, foundList.size.toLong())

        logger.info("Entity with id={} was deleted from repository", id)
    }

    private fun getEntityByIdUsingJdbcTemplate(id: Long?): Entity? {
        return jdbcTemplate!!.queryForObject("SELECT * FROM entity WHERE id=?", arrayOf(id as Any),
                rowMapper)
    }
}
