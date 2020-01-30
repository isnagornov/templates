package ru.isnagornov.templates.mapper

import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Component
import ru.isnagornov.templates.entity.Entity

@Component
interface EntityMapper : CommonMapper<Entity> {

    @get:Select("SELECT * from entity")
    override val all: List<Entity>

    @Insert("INSERT into entity(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true)
    override fun add(entity: Entity)

    @Select("SELECT * FROM entity WHERE id = #{id}")
    override fun getById(id: Long?): Entity

    @Update("UPDATE entity set name=#{name} where id=#{id}")
    override fun update(entity: Entity)

    @Delete("DELETE FROM entity WHERE id = #{id}")
    override fun delete(id: Long?)
}
