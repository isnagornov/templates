package ru.isnagornov.templates.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Entity {

    protected Long id;
    protected String name;
}
