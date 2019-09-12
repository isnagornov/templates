package ru.isnagornov.templates.service;

import ru.isnagornov.templates.entity.Entity;

import javax.ejb.Stateless;

@Stateless
public class EntityService extends AbstractService<Entity> {

    public EntityService() {
        super(Entity.class);
    }
}
