package ru.isnagornov.templates.service

import org.springframework.stereotype.Service
import ru.isnagornov.templates.entity.Entity
import ru.isnagornov.templates.mapper.EntityMapper

@Service
class EntityService(mapper: EntityMapper) : AbstractService<Entity, EntityMapper>(mapper)
