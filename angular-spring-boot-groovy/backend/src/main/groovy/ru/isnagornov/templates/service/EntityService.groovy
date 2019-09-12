package ru.isnagornov.templates.service

import org.springframework.stereotype.Service
import ru.isnagornov.templates.entity.Entity
import ru.isnagornov.templates.mapper.EntityMapper

@Service
class EntityService extends AbstractService<Entity, EntityMapper> {

    EntityService(EntityMapper mapper) {
        super(mapper)
    }

}
