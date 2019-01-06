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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.isnagornov.templates.Application;
import ru.isnagornov.templates.entity.Entity;
import ru.isnagornov.templates.mapper.EntityMapper;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntityMapperTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private BeanPropertyRowMapper<Entity> rowMapper = new BeanPropertyRowMapper<>(Entity.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityMapper entityMapper;

    @Test
    public void test1Insert() {
        final Long id = 1L;
        Entity entity = new Entity(id, "TestEntity");

        entityMapper.add(entity);

        logger.info("{} added to repository", entity);

        Entity foundEntity = getEntityByIdUsingJdbcTemplate(id);

        assertEquals(entity, foundEntity);
    }

    @Test
    public void test2Get() {
        final Long id = 1L;
        Entity foundEntity = getEntityByIdUsingJdbcTemplate(id);

        assertNotNull(foundEntity);

        Entity entity = entityMapper.getById(id);

        logger.info("{} was gotten from repository by id={}", entity, id);

        assertEquals(entity, foundEntity);
    }

    @Test
    public void test4Update() {
        Entity foundEntity = getEntityByIdUsingJdbcTemplate(1L);

        Entity entity = new Entity(1L, "TestEntity1");

        assertNotEquals(foundEntity, entity);

        entityMapper.update(entity);

        foundEntity = getEntityByIdUsingJdbcTemplate(1L);

        assertEquals(foundEntity, entity);

        logger.info("Entity was updated in repository, new value - {}", entity);
    }

    @Test
    public void test5Delete() {
        Long id = 1L;

        Entity foundEntity = getEntityByIdUsingJdbcTemplate(id);

        assertNotNull(foundEntity);

        entityMapper.delete(id);

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
