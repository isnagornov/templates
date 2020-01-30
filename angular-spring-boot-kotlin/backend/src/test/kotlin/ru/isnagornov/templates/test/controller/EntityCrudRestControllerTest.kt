package ru.isnagornov.templates.test.controller

import org.hamcrest.Matchers.hasSize
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.isnagornov.templates.entity.Entity
import ru.isnagornov.templates.mapper.EntityMapper
import ru.isnagornov.templates.rest.EntityCrudRestController
import ru.isnagornov.templates.service.EntityService
import ru.isnagornov.templates.test.util.ControllerTestsHelper.Companion.CONTENT_TYPE
import ru.isnagornov.templates.test.util.ControllerTestsHelper.Companion.JSON_MAPPER
import ru.isnagornov.templates.test.util.ControllerTestsHelper.Companion.buildMockMvc
import java.util.*

class EntityCrudRestControllerTest {

    private var mockMvc: MockMvc? = null

    @Mock
    private val mapper: EntityMapper? = null

    private var service: EntityService? = null

    private var controller: EntityCrudRestController? = null

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        service = EntityService(mapper as EntityMapper)
        controller = EntityCrudRestController(service as EntityService)

        mockMvc = buildMockMvc(controller as Any)
    }

    @Test
    @Throws(Exception::class)
    fun testAddSuccess() {
        val entity = Entity(id = 1L, name = "entity")

        mockMvc!!.perform(post("/entity/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_MAPPER.writeValueAsString(entity)))
                .andDo(print())
                .andExpect(status().isOk)

        verify<EntityMapper>(mapper, times(1)).add(entity)
        verifyNoMoreInteractions(mapper)
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateSuccess() {
        val entity = Entity(id = 1L, name = "entityUpdated!")

        mockMvc!!.perform(put("/entity/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_MAPPER.writeValueAsString(entity)))
                .andDo(print())
                .andExpect(status().isOk)

        verify<EntityMapper>(mapper, times(1)).update(entity)
        verifyNoMoreInteractions(mapper)
    }

    @Test
    @Throws(Exception::class)
    fun testDeleteSuccess() {
        val idToDelete = 1L

        mockMvc!!.perform(delete(String.format("/entity/%s", idToDelete))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk)

        verify<EntityMapper>(mapper, times(1)).delete(idToDelete)
        verifyNoMoreInteractions(mapper)
    }

    @Test
    @Throws(Exception::class)
    fun testGetByIdSuccess() {
        val id = 1L

        val entity = Entity(id = id, name = "entity1")

        `when`(mapper!!.getById(id)).thenReturn(entity)

        mockMvc!!.perform(get(String.format("/entity/%s", id)))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.id", `is`(1)))
                .andExpect(jsonPath("$.name", `is`("entity1")))

        verify(mapper, times(1))!!.getById(id)
        verifyNoMoreInteractions(mapper)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAllSuccess() {
        val list = Arrays.asList(
                Entity(1L, "entity1"),
                Entity(2L, "entity2")
        )

        `when`(mapper!!.all).thenReturn(list)

        mockMvc!!.perform(get("/entity/getAll"))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$").isArray)
                .andExpect(jsonPath("$", hasSize<Any>(list.size)))
                .andExpect(jsonPath("$[0].id", `is`(1)))
                .andExpect(jsonPath("$[0].name", `is`("entity1")))
                .andExpect(jsonPath("$[1].id", `is`(2)))
                .andExpect(jsonPath("$[1].name", `is`("entity2")))

        verify(mapper, times(1))!!.all
        verifyNoMoreInteractions(mapper)
    }
}
