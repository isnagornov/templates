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
import ru.isnagornov.templates.entity.Entity;
import ru.isnagornov.templates.service.EntityService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntityServiceTest {

    private static final Long ID = 1L;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private BeanPropertyRowMapper<Entity> rowMapper = new BeanPropertyRowMapper<>(Entity.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityService service;

    @Test
    public void test1Insert() {
        Entity entity = new Entity();
        entity.setName("TestEntity");

        service.add(entity);
        assertNotNull(entity.getId());

        logger.info("{} added to repository", entity);
    }

    @Test
    public void test2Get() {
        Entity foundEntity = getEntityByIdUsingJdbcTemplate(ID);

        assertNotNull(foundEntity);

        Entity entity = service.getById(ID);

        logger.info("{} was gotten from repository by ID={}", entity, ID);

        assertEquals(entity, foundEntity);
    }

    @Test
    public void test4Update() {
        Entity preUpdatedEntity = getEntityByIdUsingJdbcTemplate(ID);

        preUpdatedEntity.setName("updated!!!");

        service.update(preUpdatedEntity);

        Entity updatedEntity = getEntityByIdUsingJdbcTemplate(ID);

        assertEquals(preUpdatedEntity, updatedEntity);

        logger.info("Entity was updated in repository, new value - {}", updatedEntity);
    }

    @Test
    public void test5Delete() {

        Entity foundEntity = getEntityByIdUsingJdbcTemplate(ID);

        assertNotNull(foundEntity);

        service.delete(ID);

        List<Entity> foundList = jdbcTemplate.query("SELECT * FROM entity WHERE id=?", new Object[]{ID},
                rowMapper);
        assertEquals(0, foundList.size());

        logger.info("Entity with ID={} was deleted from repository", ID);
    }

    private Entity getEntityByIdUsingJdbcTemplate(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM entity WHERE id=?", new Object[]{id},
                rowMapper);
    }
}
