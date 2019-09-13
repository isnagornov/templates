package ru.isnagornov.templates.ws.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.isnagornov.templates.ws.entities.Entity;

@Service
public class EntityService extends AbstractService<Long, Entity> {

    public EntityService(JpaRepository<Entity, Long> repository) {
        super(repository);
    }
}
