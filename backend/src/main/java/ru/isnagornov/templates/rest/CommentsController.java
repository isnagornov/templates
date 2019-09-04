package ru.isnagornov.templates.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.isnagornov.templates.form.BookCommentForm;
import ru.isnagornov.templates.form.converter.BookCommentDtoConverter;
import ru.isnagornov.templates.service.impl.BookCommentService;

import javax.validation.Valid;

@Controller
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private BookCommentService bookCommentService;

    @Autowired
    private BookCommentDtoConverter bookCommentDtoConverter;

    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String save(@ModelAttribute("newComment") @Valid BookCommentForm form) {

        bookCommentService.add(bookCommentDtoConverter.getEntity(form));

        return "redirect:/books/view/" + form.getBook().getId();
    }

    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.DELETE)
    public String delete(@RequestParam("bookId") Long bookId, @PathVariable("id") Long id) {

        bookCommentService.delete(id);

        return "redirect:/books/view/" + bookId;
    }
}
