package ru.isnagornov.templates.test.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.servlet.View
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.view.json.MappingJackson2JsonView
import java.util.*

abstract class ControllerTestsHelper {

    companion object {
        val CONTENT_TYPE = "application/json;charset=UTF-8"
        val JSON_MAPPER = ObjectMapper()

        fun buildMockMvc(controller: Any): MockMvc {
            return MockMvcBuilders.standaloneSetup(controller).setViewResolvers(ViewRes()).build()
        }
    }
}

class ViewRes : ViewResolver {
    override fun resolveViewName(p0: String, p1: Locale): View? {
        return MappingJackson2JsonView()
    }
}
