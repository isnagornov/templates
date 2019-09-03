package ru.isnagornov.templates.form;

public class BookForm {

    private Long id;

    private String name;

    private String link;

    private AuthorForm author;

    private Long commentsNumber;

    public BookForm() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public AuthorForm getAuthor() {
        return author;
    }

    public void setAuthor(AuthorForm author) {
        this.author = author;
    }

    public Long getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(Long commentsNumber) {
        this.commentsNumber = commentsNumber;
    }
}
