package ru.isnagornov.templates.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Entity {

    private Long id;
    private String name;

}
