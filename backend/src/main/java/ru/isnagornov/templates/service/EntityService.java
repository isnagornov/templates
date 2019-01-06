package ru.isnagornov.templates.service;

import org.springframework.stereotype.Service;
import ru.isnagornov.templates.entity.Entity;
import ru.isnagornov.templates.mapper.CommonMapper;

@Service
public class EntityService extends AbstractService<Entity> {

    public EntityService(CommonMapper<Entity> mapper) {
        super(mapper);
    }
}
