package ru.isnagornov.templates.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.isnagornov.templates.entity.Book;
import ru.isnagornov.templates.form.BookCommentForm;
import ru.isnagornov.templates.form.BookForm;
import ru.isnagornov.templates.form.converter.AuthorDtoConverter;
import ru.isnagornov.templates.form.converter.BookCommentDtoConverter;
import ru.isnagornov.templates.form.converter.BookDtoConverter;
import ru.isnagornov.templates.repository.BookRepository;
import ru.isnagornov.templates.service.AuthorService;
import ru.isnagornov.templates.service.BookCommentService;
import ru.isnagornov.templates.service.BookService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("books")
public class BookController extends AbstractController<Long, Book, BookForm, BookRepository, BookService, BookDtoConverter> {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookCommentService bookCommentService;

    @Autowired
    private AuthorDtoConverter authorDtoConverter;

    @Autowired
    private BookCommentDtoConverter bookCommentDtoConverter;

    protected BookController(BookService service, BookDtoConverter converter) {
        super(service, converter, BookForm.class);
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    @Override
    public String showAddPage(Model model) {

        model.addAttribute("form", new BookForm());
        model.addAttribute("operation", "create");
        model.addAttribute("authors", authorService.getAll().stream().map(
                authorDtoConverter::getDto).collect(Collectors.toList()));

        return "books/operation";
    }

    @RequestMapping(value = {"/update/{id}"}, method = RequestMethod.GET)
    @Override
    public String showUpdatePage(Model model, @PathVariable("id") Long id) {

        model.addAttribute("form", getConverter().getDto(getService().getById(id)));
        model.addAttribute("operation", "update");
        model.addAttribute("authors", authorService.getAll().stream().map(
                authorDtoConverter::getDto).collect(Collectors.toList()));

        return "books/operation";
    }

    @RequestMapping(value = {"/view/{id}"}, method = RequestMethod.GET)
    public String showViewPage(Model model, @PathVariable("id") Long id) {

        model.addAttribute("form", getConverter().getDto(getService().getById(id)));
        model.addAttribute("comments", bookCommentService.getAllByBook(id).stream().map(
                bookCommentDtoConverter::getDto).collect(Collectors.toList()));

        return "books/view";
    }

    @RequestMapping(value = {"/addComment"}, method = RequestMethod.GET)
    public String showAddCommentForm(Model model, @RequestParam("bookId") Long bookId) {

        final Integer defaultRate = 5;
        final BookForm bookForm = new BookForm();
        bookForm.setId(bookId);

        BookCommentForm bookCommentForm = new BookCommentForm();
        bookCommentForm.setBook(bookForm);
        bookCommentForm.setRate(defaultRate);

        model.addAttribute("newComment", bookCommentForm);
        model.addAttribute("form", getConverter().getDto(getService().getById(bookId)));
        model.addAttribute("comments", bookCommentService.getAllByBook(bookId).stream().map(
                bookCommentDtoConverter::getDto).collect(Collectors.toList()));

        return "books/view";
    }

}
