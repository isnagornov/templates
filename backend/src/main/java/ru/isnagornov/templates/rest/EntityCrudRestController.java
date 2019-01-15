package ru.isnagornov.templates.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.isnagornov.templates.entity.Entity;
import ru.isnagornov.templates.form.EntityForm;
import ru.isnagornov.templates.service.EntityService;

@Controller
public class EntityCrudRestController {

    @Autowired
    private EntityService entityService;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value = {"/entityList"}, method = RequestMethod.GET)
    public String personList(Model model) {

        model.addAttribute("entities", entityService.getAll());

        return "entityList";
    }

    @RequestMapping(value = {"/addEntity"}, method = RequestMethod.GET)
    public String showAddPage(Model model) {

        EntityForm entityForm = new EntityForm();

        model.addAttribute("entityForm", entityForm);

        return "addEntity";
    }

    @RequestMapping(value = {"/addEntity"}, method = RequestMethod.POST)
    public String savePerson(Model model, @ModelAttribute("entityForm") EntityForm entityForm) {

        Long id = entityForm.getId();
        String name = entityForm.getName();

        if (!StringUtils.isEmpty(name)) {
            Entity entity = new Entity(id, name);

            entityService.add(entity);

            return "redirect:/entityList";
        }

        model.addAttribute("errorMessage", errorMessage);

        return "addEntity";
    }

}
