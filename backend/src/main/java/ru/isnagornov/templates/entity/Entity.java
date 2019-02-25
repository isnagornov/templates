package ru.isnagornov.templates.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "entity")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Entity extends AbstractEntity<Long> {

    @Column
    protected String name;

    public Entity(Long id, String name) {
        super(id);

        this.name = name;
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return super.getId();
    }
}
