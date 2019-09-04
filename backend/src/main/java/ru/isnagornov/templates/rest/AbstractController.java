package ru.isnagornov.templates.rest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.isnagornov.templates.form.converter.BaseConverter;
import ru.isnagornov.templates.service.AbstractService;

import javax.validation.Valid;
import java.util.stream.Collectors;

public abstract class AbstractController<ID, E, DTO, R extends JpaRepository<E, ID>,
        S extends AbstractService<ID, E, R>, C extends BaseConverter<E, DTO>> {

    private S service;

    private C converter;

    private Class<DTO> dtoClass;

    private String controllerId;

    protected AbstractController(S service, C converter, Class<DTO> dtoClass) {
        this.service = service;
        this.converter = converter;
        this.dtoClass = dtoClass;

        this.controllerId = getControllerId();
    }

    public S getService() {
        return service;
    }

    public C getConverter() {
        return converter;
    }

    private String getControllerId() {
        return getClass().getAnnotation(RequestMapping.class).value()[0];
    }

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(Model model) {

        model.addAttribute(controllerId, service.getAll().stream().map(
                converter::getDto).collect(Collectors.toList()));

        return controllerId + "/list";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String showAddPage(Model model) {

        try {
            model.addAttribute("form", dtoClass.newInstance());

            model.addAttribute("operation", "create");
            model.addAttribute(controllerId, service.getAll().stream().map(
                    converter::getDto).collect(Collectors.toList()));
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return controllerId + "/operation";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String save(Model model, @ModelAttribute("form") @Valid DTO form) {

        try {
            service.add(converter.getEntity(form));

            return String.format("redirect:/%s/list", controllerId);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return controllerId + "/operation";
        }
    }

    @RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
    public String showUpdatePage(Model model, @PathVariable("id") ID id) {

        model.addAttribute("form", converter.getDto(service.getById(id)));
        model.addAttribute("operation", "update");
        model.addAttribute(controllerId, service.getAll().stream().map(
                converter::getDto).collect(Collectors.toList()));

        return controllerId + "/operation";
    }

    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    public String update(Model model, @ModelAttribute("form") @Valid DTO form) {

        try {
            service.update(converter.getEntity(form));

            return String.format("redirect:/%s/list", controllerId);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return controllerId + "/operation";
        }
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.DELETE)
    public String delete(Model model, @PathVariable("id") ID id) {

        try {
            service.delete(id);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return list(model);
        }

        return String.format("redirect:/%s/list", controllerId);
    }
}
