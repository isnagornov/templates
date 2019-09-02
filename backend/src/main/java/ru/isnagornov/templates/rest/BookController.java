package ru.isnagornov.templates.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.isnagornov.templates.form.BookCommentForm;
import ru.isnagornov.templates.form.BookForm;
import ru.isnagornov.templates.form.converter.AuthorDtoConverter;
import ru.isnagornov.templates.form.converter.BookCommentDtoConverter;
import ru.isnagornov.templates.form.converter.BookDtoConverter;
import ru.isnagornov.templates.service.AuthorService;
import ru.isnagornov.templates.service.BookCommentService;
import ru.isnagornov.templates.service.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookCommentService bookCommentService;

    @Autowired
    private BookDtoConverter bookDtoConverter;

    @Autowired
    private AuthorDtoConverter authorDtoConverter;

    @Autowired
    private BookCommentDtoConverter bookCommentDtoConverter;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(Model model) {

        model.addAttribute("books", bookService.getAll().stream().map(
                bookDtoConverter::getDto).collect(Collectors.toList()));

        return "books/list";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String showAddPage(Model model) {

        model.addAttribute("form", new BookForm());
        model.addAttribute("operation", "create");
        model.addAttribute("authors", authorService.getAll().stream().map(
                authorDtoConverter::getDto).collect(Collectors.toList()));

        return "books/operation";
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String save(Model model, @ModelAttribute("form") @Valid BookForm form) {

        try {
            bookService.add(bookDtoConverter.getEntity(form));

            return "redirect:/books/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "books/operation";
        }
    }

    @RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
    public String showUpdatePage(Model model, @PathVariable("id") Long id) {

        model.addAttribute("form", bookDtoConverter.getDto(bookService.getById(id)));
        model.addAttribute("operation", "update");
        model.addAttribute("authors", authorService.getAll().stream().map(
                authorDtoConverter::getDto).collect(Collectors.toList()));

        return "books/operation";
    }

    @RequestMapping(value = {"/view/{id}"}, method = RequestMethod.GET)
    public String showViewPage(Model model, @PathVariable("id") Long id) {

        model.addAttribute("form", bookDtoConverter.getDto(bookService.getById(id)));
        model.addAttribute("comments", bookCommentService.getAllByBook(id).stream().map(
                bookCommentDtoConverter::getDto).collect(Collectors.toList()));

        return "books/view";
    }

    @RequestMapping(value = {"/update"}, method = RequestMethod.POST)
    public String update(Model model, @ModelAttribute("form") @Valid BookForm form) {

        try {
            bookService.update(bookDtoConverter.getEntity(form));

            return "redirect:/books/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "books/operation";
        }
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {

        bookService.delete(id);

        return "redirect:/books/list";
    }

    @RequestMapping(value = {"/addComment"}, method = RequestMethod.GET)
    public String showAddCommentForm(Model model, @RequestParam("bookId") Long bookId) {

        BookForm bookForm = new BookForm();
        bookForm.setId(bookId);

        BookCommentForm bookCommentForm = new BookCommentForm();
        bookCommentForm.setBook(bookForm);

        model.addAttribute("newComment", bookCommentForm);
        model.addAttribute("form", bookDtoConverter.getDto(bookService.getById(bookId)));
        model.addAttribute("comments", bookCommentService.getAllByBook(bookId).stream().map(
                bookCommentDtoConverter::getDto).collect(Collectors.toList()));

        return "books/view";
    }

}
