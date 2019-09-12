package ru.isnagornov.templates.test.mappers


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import ru.isnagornov.templates.entity.Book
import ru.isnagornov.templates.test.util.DbUtil

import java.sql.ResultSet
import java.sql.SQLException

@Component
class BookMapper implements RowMapper<Book> {

    @Autowired
    private DbUtil dbUtil

    @Override
    Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book()

        book.id = resultSet.getLong("id")
        book.name = resultSet.getString("name")
        book.link = resultSet.getString("link")
        book.author = dbUtil.getEntityById(resultSet.getLong("author_id"), "author",
                DbUtil.authorRowMapper)

        book
    }
}
