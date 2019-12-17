package ru.isnagornov.templates.test.service;

import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.isnagornov.templates.entity.Entity;
import ru.isnagornov.templates.service.EntityService;
import ru.isnagornov.templates.test.service.config.TestDbConfig;
import ru.isnagornov.templates.test.util.TestPostgresqlContainer;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(classes = {TestDbConfig.class})
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntityServiceTest {

    @ClassRule
    public static final PostgreSQLContainer postgreSQLContainer = TestPostgresqlContainer.getInstance()
            .withDatabaseName("testDB")
            .withUsername("testUser")
            .withPassword("testPassword");

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private BeanPropertyRowMapper<Entity> rowMapper = new BeanPropertyRowMapper<>(Entity.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityService entityService;

    @Test
    public void test1Insert() {
        final Long id = 1L;
        Entity entity = new Entity(null, "TestEntity");

        entityService.add(entity);
        assertEquals(id, entity.getId());

        logger.info("{} added to repository", entity);

        Entity foundEntity = getEntityByIdUsingJdbcTemplate(id);

        assertEquals(entity, foundEntity);
    }

    @Test
    public void test2Get() {
        final Long id = 1L;
        Entity foundEntity = getEntityByIdUsingJdbcTemplate(id);

        assertNotNull(foundEntity);

        Entity entity = entityService.getById(id);

        logger.info("{} was gotten from repository by id={}", entity, id);

        assertEquals(entity, foundEntity);
    }

    @Test
    public void test4Update() {
        Entity foundEntity = getEntityByIdUsingJdbcTemplate(1L);

        Entity entity = new Entity(1L, "TestEntity1");

        assertNotEquals(foundEntity, entity);

        entityService.update(entity);

        foundEntity = getEntityByIdUsingJdbcTemplate(1L);

        assertEquals(foundEntity, entity);

        logger.info("Entity was updated in repository, new value - {}", entity);
    }

    @Test
    public void test5Delete() {
        Long id = 1L;

        Entity foundEntity = getEntityByIdUsingJdbcTemplate(id);

        assertNotNull(foundEntity);

        entityService.delete(id);

        List<Entity> foundList = jdbcTemplate.query("SELECT * FROM entity WHERE id=?", new Object[]{id},
                rowMapper);
        assertEquals(0, foundList.size());

        logger.info("Entity with id={} was deleted from repository", id);
    }

    private Entity getEntityByIdUsingJdbcTemplate(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM entity WHERE id=?", new Object[]{id},
                rowMapper);
    }
}
