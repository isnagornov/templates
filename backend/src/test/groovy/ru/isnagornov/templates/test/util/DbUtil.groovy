package ru.isnagornov.templates.test.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.support.DataAccessUtils
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Component
import ru.isnagornov.templates.entity.Author
import ru.isnagornov.templates.entity.Book

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

@Component
class DbUtil {

    public static final RowMapper<Author> authorRowMapper = new BeanPropertyRowMapper<>(Author)

    @Autowired
    private JdbcTemplate jdbcTemplate

    Long insertBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder()

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into book (name, link, rate, author_id) values (?, ?, ?, ?)",
                        ["name", "link", "rate", "author_id"] as String[])

                preparedStatement.setString(1, book.name)
                preparedStatement.setString(2, book.link)
                preparedStatement.setInt(3, book.rate)
                preparedStatement.setLong(4, book.author.id)

                preparedStatement
            }
        }, keyHolder)

        keyHolder.key
    }

    def <T> T getEntityById(Long id, String tableName, RowMapper<T> mapper) {
        DataAccessUtils.uniqueResult(queryForList("SELECT * FROM ${tableName} WHERE id=${id}", mapper))
    }

    def <T> List<T> queryForList(String query, RowMapper<T> rowMapper) {
        jdbcTemplate.query(query, rowMapper)
    }

    void deleteEntityById(String tableName, Long id) {
        jdbcTemplate.execute("delete from ${tableName} where id = ${id}")
    }
}
