package ru.isnagornov.templates.form;

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
}
