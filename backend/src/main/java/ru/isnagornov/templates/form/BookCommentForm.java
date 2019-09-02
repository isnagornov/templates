package ru.isnagornov.templates.form;

import java.util.Objects;

public class BookCommentForm {

    private Long id;

    private String comment;

    private String user;

    private Integer rate;

    private BookForm book;

    public BookCommentForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public BookForm getBook() {
        return book;
    }

    public void setBook(BookForm book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCommentForm that = (BookCommentForm) o;
        return id.equals(that.id) &&
                comment.equals(that.comment) &&
                Objects.equals(user, that.user) &&
                Objects.equals(book, that.book) &&
                Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment, user, book, rate);
    }
}
