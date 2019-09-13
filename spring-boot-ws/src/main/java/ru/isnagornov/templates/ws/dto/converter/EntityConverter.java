package ru.isnagornov.templates.ws.dto.converter;

import org.springframework.stereotype.Component;
import ru.isnagornov.templates.ws.entities.Entity;

@Component
public class EntityConverter extends BaseConverter<Entity, ru.isnagornov.templates.ws.dto.entity.Entity> {

    public EntityConverter() {
        super(Entity.class, ru.isnagornov.templates.ws.dto.entity.Entity.class);
    }
}
