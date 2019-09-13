package ru.isnagornov.templates.ws.dto.converter;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

public abstract class BaseConverter<A, B> {

    private Class<A> aClass;
    private Class<B> bClass;

    private MapperFactory mapperFactory;

    private ClassMapBuilder<A, B> classMapBuilder;

    public BaseConverter(Class<A> aClass, Class<B> bClass) {
        this.aClass = aClass;
        this.bClass = bClass;

        mapperFactory = new DefaultMapperFactory.Builder().build();
        classMapBuilder = mapperFactory.classMap(aClass, bClass);
    }

    public ClassMapBuilder<A, B> getClassMapBuilder() {
        return classMapBuilder;
    }

    public B getDto(A entity) {
        return getMapperFacade().map(entity);
    }

    public A getEntity(B dto) {
        return getMapperFacade().mapReverse(dto);
    }

    private BoundMapperFacade<A, B> getMapperFacade() {
        return mapperFactory.getMapperFacade(this.aClass, this.bClass);
    }
}
