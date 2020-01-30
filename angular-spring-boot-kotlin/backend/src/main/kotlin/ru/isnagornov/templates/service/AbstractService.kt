package ru.isnagornov.templates.service

import ru.isnagornov.templates.mapper.CommonMapper

abstract class AbstractService<T, M : CommonMapper<T>>(protected var mapper: M) {

    val all: List<T>
        get() = this.mapper.all

    fun add(entity: T) {
        this.mapper.add(entity)
    }

    fun getById(id: Long?): T {
        return this.mapper.getById(id)
    }

    fun update(entity: T) {
        this.mapper.update(entity)
    }

    fun delete(id: Long?) {
        this.mapper.delete(id)
    }
}
