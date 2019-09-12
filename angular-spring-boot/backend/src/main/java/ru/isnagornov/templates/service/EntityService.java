package ru.isnagornov.templates.service;

import org.springframework.stereotype.Service;
import ru.isnagornov.templates.entity.Entity;
import ru.isnagornov.templates.mapper.CommonMapper;
import ru.isnagornov.templates.mapper.EntityMapper;

@Service
public class EntityService extends AbstractService<Entity, EntityMapper> {

    public EntityService(EntityMapper mapper) {
        super(mapper);
    }
}
