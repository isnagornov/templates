package ru.isnagornov.templates.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.isnagornov.templates.entity.AbstractEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EntityDto extends AbstractEntity<Long> {

    protected String name;

    public EntityDto(Long id, String name) {
        super(id);

        this.name = name;
    }
}
