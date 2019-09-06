package ru.isnagornov.templates.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.isnagornov.templates.entity.Author;
import ru.isnagornov.templates.form.AuthorForm;
import ru.isnagornov.templates.form.FindAuthorForm;
import ru.isnagornov.templates.form.converter.AuthorDtoConverter;
import ru.isnagornov.templates.repository.AuthorRepository;
import ru.isnagornov.templates.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("authors")
public class AuthorController extends AbstractController<Long, Author, AuthorForm, AuthorRepository, AuthorService,
        AuthorDtoConverter> {

    @Autowired
    protected AuthorController(AuthorService service, AuthorDtoConverter converter) {
        super(service, converter, AuthorForm.class);
    }

    @RequestMapping(value = {"/byName"}, method = RequestMethod.POST)
    public ModelAndView byName(@RequestParam("nameToSearch") String nameToSearch) {

        ModelAndView modelAndView = new ModelAndView("books/list::foundAuthors");

        List<AuthorForm> foundAuthors = getService().findByNameContainsIgnoreCase(nameToSearch)
                .stream().map(getConverter()::getDto).collect(Collectors.toList());

        FindAuthorForm findAuthorForm = new FindAuthorForm();
        findAuthorForm.setFoundAuthors(foundAuthors);
        modelAndView.addObject("findAuthorForm", findAuthorForm);

        return modelAndView;
    }
}
