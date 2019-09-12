package ru.isnagornov.templates.entity;

import javax.persistence.*;
import java.util.Objects;

@javax.persistence.Entity
@NamedQueries({
        @NamedQuery(name = Entity.FIND_ALL_QUERY, query = "select e from Entity e")
})
@Table(name = "entity")
public class Entity {

    public static final String FIND_ALL_QUERY = "Entity.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Entity() {
    }

    public Entity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity entity = (Entity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
