package ru.isnagornov.templates.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class AbstractService<T> {

    private Class<T> type;

    @PersistenceContext(unitName = "entity-persistence-unit")
    protected EntityManager entityManager;

    protected AbstractService(Class<T> type) {
        this.type = type;
    }

    public void add(T entity) {
        entityManager.persist(entity);
    }

    public T getById(Long id) {
        return entityManager.find(type, id);
    }

    public List<T> getAll() {
        String query;

        try {
            query = (String) type.getField("FIND_ALL_QUERY").get(type);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("Can't find FIND_ALL_QUERY for class %s", type), e);
        }

        return (List<T>) entityManager.createNamedQuery(query).getResultList();
    }

    public void update(T entity) {
        entityManager.merge(entity);
    }

    public void delete(Long id) {
        entityManager.remove(entityManager.find(type, id));
    }
}
