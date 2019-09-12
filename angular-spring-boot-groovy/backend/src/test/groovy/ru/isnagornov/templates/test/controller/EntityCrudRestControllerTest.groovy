package ru.isnagornov.templates.test.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import ru.isnagornov.templates.entity.Entity
import ru.isnagornov.templates.rest.EntityCrudRestController
import ru.isnagornov.templates.service.EntityService
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [EntityCrudRestController])
class EntityCrudRestControllerTest extends Specification {

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8"

    @Autowired
    private MockMvc mvc

    @Autowired
    private EntityService service

    @Autowired
    private ObjectMapper objectMapper

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        EntityService entityService() {
            return detachedMockFactory.Mock(EntityService)
        }
    }


    def "should post json entity to rest and return ok http status"() {
        given:
        Entity entity = [
                id  : 1L,
                name: 'entity'
        ] as Entity

        when:
        def results = mvc.perform(post('/entity/add').contentType(MediaType.APPLICATION_JSON_UTF8).content(
                objectMapper.writeValueAsString(entity))).andDo(print())

        then:
        results.andExpect(status().isOk())

        and:
        with(service) {
            1 * add(entity)
        }
    }

    def "should put json entity to rest and return ok http status"() {
        given:
        Entity entity = new Entity(1L, "EntityToUpdate")

        when:
        def results = mvc.perform(put('/entity/update').contentType(MediaType.APPLICATION_JSON_UTF8).content(
                objectMapper.writeValueAsString(entity))).andDo(print())

        then:
        results.andExpect(status().isOk())

        and:
        with(service) {
            1 * update(entity)
        }
    }

    def "should send entity id for delete to rest and return ok http status"() {
        given:
        final Long idToDelete = 1L

        when:
        def results = mvc.perform(delete("/entity/${idToDelete}").contentType(MediaType.APPLICATION_JSON)).andDo(print())

        then:
        results.andExpect(status().isOk())

        and:
        with(service) {
            1 * it.delete(idToDelete)
        }
    }

    def "should send entity id to rest and return json entity with ok http status"() {
        given:
        final Long id = 1L

        and:
        service.getById(id) >> new Entity(1L, "Entity1")

        when:
        def results = mvc.perform(get("/entity/${id}").contentType(MediaType.APPLICATION_JSON)).andDo(print())

        then:
        results.andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath('$.id').value(1))
                .andExpect(jsonPath('$.name').value('Entity1'))
    }

    def "should get all entities as json array with ok http status"() {
        given:
        List<Entity> resultList = [new Entity(1L, "Entity1"), new Entity(2L, "Entity2")]

        and:
        service.getAll() >> resultList

        when:
        def results = mvc.perform(get("/entity/getAll")).andDo(print())

        then:
        results.andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath('$').isArray())
                .andExpect(jsonPath('$', Matchers.hasSize(resultList.size())))
                .andExpect(jsonPath('$[0].id', Matchers.is(1)))
                .andExpect(jsonPath('$[0].name', Matchers.is("Entity1")))
                .andExpect(jsonPath('$[1].id', Matchers.is(2)))
                .andExpect(jsonPath('$[1].name', Matchers.is("Entity2")))
    }
}

