package ru.isnagornov.templates.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.isnagornov.templates.entity.Entity;
import ru.isnagornov.templates.form.EntityDto;
import ru.isnagornov.templates.service.EntityService;

import javax.validation.Valid;

@Controller
public class EntityCrudRestController {

    @Autowired
    private EntityService entityService;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = {"/entityList"}, method = RequestMethod.GET)
    public String list(Model model) {

        model.addAttribute("entities", entityService.getAll());

        return "entityList";
    }

    @RequestMapping(value = {"/addEntity"}, method = RequestMethod.GET)
    public String showAddPage(Model model) {

        model.addAttribute("entityDto", new EntityDto());
        model.addAttribute("operation", "create");

        return "entityOperation";
    }

    @RequestMapping(value = {"/addEntity"}, method = RequestMethod.POST)
    public String save(Model model, @ModelAttribute("entityDto") @Valid EntityDto entityDto) {

        try {
            Entity entity = new Entity();

            entity.setName(entityDto.getName());

            entityService.add(entity);

            return "redirect:/entityList";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "entityOperation";
        }
    }

    @RequestMapping(value = {"/updateEntity/{id}"}, method = RequestMethod.GET)
    public String showUpdatePage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("entityDto", entityService.getById(id));
        model.addAttribute("operation", "update");

        return "entityOperation";
    }

    @RequestMapping(value = {"/updateEntity/{id}"}, method = RequestMethod.POST)
    public String update(Model model, @PathVariable("id") Long id,
                         @ModelAttribute("entityDto") @Valid EntityDto entityDto) {

        try {
            Entity entityToUpdate = entityService.getById(id);

            entityToUpdate.setName(entityDto.getName());

            entityService.update(entityToUpdate);

            return "redirect:/entityList";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "entityOperation";
        }
    }

    @RequestMapping(value = {"/deleteEntity/{id}"}, method = RequestMethod.DELETE)
    public String delete(Model model, @PathVariable("id") Long id) {

        entityService.delete(id);

        model.addAttribute("entities", entityService.getAll());

        return "redirect:/entityList";
    }

}
