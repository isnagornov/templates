package ru.isnagornov.templates.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import ru.isnagornov.templates.entity.Entity;

import java.util.List;

@Component
public interface EntityMapper extends CommonMapper<Entity> {

    @Override
    @Insert("INSERT into entity(id, name) VALUES(#{id}, #{name})")
    void add(Entity entity);

    @Override
    @Select("SELECT * FROM entity WHERE id = #{id}")
    Entity getById(Long id);

    @Override
    @Select("SELECT * from entity")
    List<Entity> getAll();

    @Override
    @Update("UPDATE entity set name=#{name} where id=#{id}")
    void update(Entity entity);

    @Override
    @Delete("DELETE FROM entity WHERE id = #{id}")
    void delete(Long id);
}
