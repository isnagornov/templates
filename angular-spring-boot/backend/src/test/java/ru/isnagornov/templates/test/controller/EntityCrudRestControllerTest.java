package ru.isnagornov.templates.test.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.isnagornov.templates.entity.Entity;
import ru.isnagornov.templates.rest.EntityCrudRestController;
import ru.isnagornov.templates.service.EntityService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.isnagornov.templates.test.util.ControllerTestsHelper.*;

public class EntityCrudRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EntityService service;

    @InjectMocks
    private EntityCrudRestController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        mockMvc = buildMockMvc(controller);
    }

    @Test
    public void testAddSuccess() throws Exception {
        Entity entity = Entity.builder()
                .id(1L).name("entity")
                .build();

        mockMvc.perform(post("/entity/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_MAPPER.writeValueAsString(entity)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service, times(1)).add(entity);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        Entity entity = Entity.builder()
                .id(1L).name("entityUpdated!")
                .build();

        mockMvc.perform(put("/entity/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_MAPPER.writeValueAsString(entity)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service, times(1)).update(entity);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        final Long idToDelete = 1L;

        mockMvc.perform(delete(String.format("/entity/%s", idToDelete))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service, times(1)).delete(idToDelete);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testGetByIdSuccess() throws Exception {
        final Long id = 1L;

        Entity entity = Entity.builder().id(id).name("entity1").build();

        when(service.getById(id)).thenReturn(entity);

        mockMvc.perform(get(String.format("/entity/%s", id)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("entity1")));

        verify(service, times(1)).getById(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testGetAllSuccess() throws Exception {
        List<Entity> list = Arrays.asList(
                Entity.builder().id(1L).name("entity1").build(),
                Entity.builder().id(2L).name("entity2").build()
        );

        when(service.getAll()).thenReturn(list);

        mockMvc.perform(get("/entity/getAll"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(list.size())))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("entity1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("entity2")));

        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }
}
