package ru.isnagornov.templates.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public abstract class ControllerTestsHelper {

    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private ControllerTestsHelper() {
    }

    public static MockMvc buildMockMvc(Object controller) {
        return MockMvcBuilders.standaloneSetup(controller).setViewResolvers((ViewResolver) (viewName, locale) -> new MappingJackson2JsonView()).build();
    }
}
