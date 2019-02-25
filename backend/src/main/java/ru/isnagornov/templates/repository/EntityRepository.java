package ru.isnagornov.templates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.isnagornov.templates.entity.Entity;

@Repository
public interface EntityRepository extends JpaRepository<Entity, Long> {
}
