package ru.isnagornov.templates.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isnagornov.templates.ws.entities.Entity;


@Repository
public interface EntityRepository extends JpaRepository<Entity, Long> {
}
