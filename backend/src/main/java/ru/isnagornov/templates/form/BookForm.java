package ru.isnagornov.templates.form;

public class BookForm {

    private Long id;

    private String name;

    private String link;

    private Integer rate;

    private AuthorForm author;

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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public AuthorForm getAuthor() {
        return author;
    }

    public void setAuthor(AuthorForm author) {
        this.author = author;
    }
}
