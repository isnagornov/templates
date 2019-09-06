package ru.isnagornov.templates.form;

import java.util.List;

public class FindAuthorForm {

    private Long selectedAuthorId;
    private List<AuthorForm> foundAuthors;

    public Long getSelectedAuthorId() {
        return selectedAuthorId;
    }

    public void setSelectedAuthorId(Long selectedAuthorId) {
        this.selectedAuthorId = selectedAuthorId;
    }

    public List<AuthorForm> getFoundAuthors() {
        return foundAuthors;
    }

    public void setFoundAuthors(List<AuthorForm> foundAuthors) {
        this.foundAuthors = foundAuthors;
    }

}
