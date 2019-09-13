package ru.isnagornov.templates.ws.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.isnagornov.templates.ws.dto.converter.EntityConverter;
import ru.isnagornov.templates.ws.dto.entity.*;
import ru.isnagornov.templates.ws.entities.Entity;
import ru.isnagornov.templates.ws.service.EntityService;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class EntitiesEndpoint {

    private static final String NAMESPACE_URI = "http://www.isnagornov.ru/templates/ws/dto/entity";

    private EntityService entityService;

    private EntityConverter entityConverter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "insertEntityRequest")
    @ResponsePayload
    public InsertEntityResponse add(@RequestPayload InsertEntityRequest request) {
        InsertEntityResponse response = new InsertEntityResponse();

        Entity added = entityService.add(entityConverter.getEntity(request.getEntity()));

        response.setEntity(entityConverter.getDto(added));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEntityRequest")
    @ResponsePayload
    public GetEntityResponse get(@RequestPayload GetEntityRequest request) {
        GetEntityResponse response = new GetEntityResponse();

        response.setEntity(entityConverter.getDto(entityService.getById(request.getId())));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllEntityRequest")
    @ResponsePayload
    public GetAllEntityResponse getList() {
        GetAllEntityResponse response = new GetAllEntityResponse();

        List<ru.isnagornov.templates.ws.dto.entity.Entity> entityList = entityService.getAll().stream().map(
                entity -> entityConverter.getDto(entity)).collect(Collectors.toList());
        response.getEntities().addAll(entityList);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEntityRequest")
    @ResponsePayload
    public DeleteEntityResponse delete(@RequestPayload DeleteEntityRequest request) {
        DeleteEntityResponse response = new DeleteEntityResponse();

        entityService.delete(request.getId());

        response.setStatus(HttpStatus.OK.value());

        return response;
    }

    @Autowired
    public void setEntityService(EntityService entityService) {
        this.entityService = entityService;
    }

    @Autowired
    public void setEntityConverter(EntityConverter entityConverter) {
        this.entityConverter = entityConverter;
    }
}
