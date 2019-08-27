package ru.isnagornov.templates.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.isnagornov.templates.entity.Book;
import ru.isnagornov.templates.form.BookForm;
import ru.isnagornov.templates.service.BookService;

import javax.validation.Valid;

@Controller
@RequestMapping("books")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(Model model) {

        model.addAttribute("books", bookService.getAll());

        return "books/list";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String showAddPage(Model model) {

        model.addAttribute("form", new BookForm());
        model.addAttribute("operation", "create");

        return "books/operation";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String save(Model model, @ModelAttribute("form") @Valid BookForm form) {

        try {
            Book book = new Book();

            book.setName(form.getName());

            bookService.add(book);

            return "redirect:/books/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "books/operation";
        }
    }

    @RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
    public String showUpdatePage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("form", bookService.getById(id));
        model.addAttribute("operation", "update");

        return "books/operation";
    }

    @RequestMapping(value = {"/update/{id}"}, method = RequestMethod.POST)
    public String update(Model model, @PathVariable("id") Long id,
                         @ModelAttribute("form") @Valid BookForm form) {

        try {
            Book bookToUpdate = bookService.getById(id);

            bookToUpdate.setName(form.getName());

            bookService.update(bookToUpdate);

            return "redirect:/books/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "books/operation";
        }
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.DELETE)
    public String delete(Model model, @PathVariable("id") Long id) {

        bookService.delete(id);

        model.addAttribute("books", bookService.getAll());

        return "redirect:/books/list";
    }

}
