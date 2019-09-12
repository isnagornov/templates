package ru.isnagornov.templates.rest

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.isnagornov.templates.entity.Entity
import ru.isnagornov.templates.mapper.EntityMapper
import ru.isnagornov.templates.service.EntityService

@RestController
@RequestMapping('entity')
class EntityCrudRestController extends AbstractController<Entity, EntityMapper, EntityService> {

    protected EntityCrudRestController(EntityService service) {
        super(service)
    }

}