package ru.isnagornov.templates.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.isnagornov.templates.form.converter.BookCommentDtoConverter;
import ru.isnagornov.templates.form.converter.BookDtoConverter;
import ru.isnagornov.templates.service.BookCommentService;
import ru.isnagornov.templates.service.BookService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private BookCommentService bookCommentService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookDtoConverter bookDtoConverter;

    @Autowired
    private BookCommentDtoConverter bookCommentDtoConverter;

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.DELETE)
    public String delete(Model model, @RequestParam("bookId") Long bookId, @PathVariable("id") Long id) {

        bookCommentService.delete(id);

        model.addAttribute("form", bookDtoConverter.getDto(bookService.getById(bookId)));
        model.addAttribute("comments", bookCommentService.getAllByBook(id).stream().map(
                bookCommentDtoConverter::getDto).collect(Collectors.toList()));

        return "redirect:/books/view/" + bookId;
    }
}
