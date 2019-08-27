package ru.isnagornov.templates.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.isnagornov.templates.form.AuthorForm;
import ru.isnagornov.templates.form.converter.AuthorDtoConverter;
import ru.isnagornov.templates.service.AuthorService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorDtoConverter authorDtoConverter;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(Model model) {

        model.addAttribute("authors", authorService.getAll().stream().map(
                authorDtoConverter::getDto).collect(Collectors.toList()));

        return "authors/list";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String showAddPage(Model model) {

        model.addAttribute("form", new AuthorForm());
        model.addAttribute("operation", "create");
        model.addAttribute("authors", authorService.getAll().stream().map(
                authorDtoConverter::getDto).collect(Collectors.toList()));

        return "authors/operation";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String save(Model model, @ModelAttribute("form") @Valid AuthorForm form) {

        try {
            authorService.add(authorDtoConverter.getEntity(form));

            return "redirect:/authors/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "authors/operation";
        }
    }

    @RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
    public String showUpdatePage(Model model, @PathVariable("id") Long id) {

        model.addAttribute("form", authorDtoConverter.getDto(authorService.getById(id)));
        model.addAttribute("operation", "update");
        model.addAttribute("authors", authorService.getAll().stream().map(
                authorDtoConverter::getDto).collect(Collectors.toList()));

        return "authors/operation";
    }

    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    public String update(Model model, @ModelAttribute("form") @Valid AuthorForm form) {

        try {
            authorService.update(authorDtoConverter.getEntity(form));

            return "redirect:/authors/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "authors/operation";
        }
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {

        authorService.delete(id);

        return "redirect:/authors/list";
    }
}
