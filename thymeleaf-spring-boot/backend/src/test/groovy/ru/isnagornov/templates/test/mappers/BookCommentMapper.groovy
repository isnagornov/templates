package ru.isnagornov.templates.test.mappers


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import ru.isnagornov.templates.entity.Book
import ru.isnagornov.templates.entity.BookComment
import ru.isnagornov.templates.test.util.DbUtil

import java.sql.ResultSet
import java.sql.SQLException

@Component
class BookCommentMapper implements RowMapper<BookComment> {

    @Autowired
    private DbUtil dbUtil

    @Autowired
    private BookMapper bookMapper

    @Override
    BookComment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        BookComment bookComment = new BookComment()

        bookComment.id = resultSet.getLong("id")
        bookComment.comment = resultSet.getString("book_comment")
        bookComment.rate = resultSet.getInt("rate")
        bookComment.user = resultSet.getString("user_commented")
        bookComment.book = dbUtil.getEntityById(resultSet.getLong("book_id"), "book",
                bookMapper)

        bookComment
    }
}
