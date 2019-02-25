package ru.isnagornov.templates.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.isnagornov.templates.entity.Entity;
import ru.isnagornov.templates.form.EntityDto;
import ru.isnagornov.templates.service.EntityService;

import javax.servlet.http.HttpServletRequest;

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

        EntityDto entityDto = new EntityDto();

        model.addAttribute("entityDto", entityDto);

        return "addEntity";
    }

    @RequestMapping(value = {"/addEntity"}, method = RequestMethod.POST)
    public String save(Model model, @ModelAttribute("entityDto") EntityDto entityDto) {

        Long id = entityDto.getId();
        String name = entityDto.getName();

        if (!StringUtils.isEmpty(name)) {
            Entity entity = new Entity(id, name);

            entityService.add(entity);

            return "redirect:/entityList";
        }

        model.addAttribute("errorMessage", errorMessage);

        return "addEntity";
    }

    @RequestMapping(value = {"/deleteEntity/{id}"}, method = RequestMethod.DELETE)
    public String delete(Model model, @PathVariable("id") Long id) {

        entityService.delete(id);

        model.addAttribute("entities", entityService.getAll());

        return "redirect:/entityList";
    }

}
