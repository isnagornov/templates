package ru.isnagornov.templates.entity

import java.util.*

class Entity(var id: Long? = null, var name: String? = null) {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val entity = o as Entity?
        return id == entity!!.id && name == entity.name
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name)
    }

    override fun toString(): String {
        return "Entity{" +
                "id=" + id +
                ", name='" + name + '\''.toString() +
                '}'.toString()
    }
}

