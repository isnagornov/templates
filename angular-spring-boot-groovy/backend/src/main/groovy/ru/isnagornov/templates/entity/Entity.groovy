package ru.isnagornov.templates.entity

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

@TupleConstructor
@ToString(includeFields = true, includeNames = true)
@EqualsAndHashCode
class Entity {
    Long id
    String name
}
