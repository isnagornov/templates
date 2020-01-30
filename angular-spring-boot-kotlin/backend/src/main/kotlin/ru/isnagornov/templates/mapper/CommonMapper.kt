package ru.isnagornov.templates.mapper

interface CommonMapper<T> {

    val all: List<T>

    fun add(entity: T)

    fun getById(id: Long?): T

    fun update(entity: T)

    fun delete(id: Long?)
}
