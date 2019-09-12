package ru.isnagornov.templates.rest;

import ru.isnagornov.templates.entity.Entity;
import ru.isnagornov.templates.service.EntityService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

@Stateless
@Path("/entity")
public class EntityCrudRestController extends AbstractController<Entity, EntityService> {

    @Inject
    public EntityCrudRestController(EntityService service) {
        super(service);
    }

}
