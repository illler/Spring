package org.crud.dao;

import org.crud.models.Book;
import org.crud.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("Select * from Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book){
        jdbcTemplate.update("insert into book(title, year, author) values (?, ?, ?)", book.getTitle(),
                book.getYear(), book.getAuthor());
    }

    public void update(int id, Book book){
        jdbcTemplate.update("update book set title=?, year=?, author=? where id=?", book.getTitle(),
                book.getYear(), book.getAuthor(), id);
    }
    public Book show(int id){
        return jdbcTemplate.query("select * from book where id=?", new BeanPropertyRowMapper<>(Book.class), new Object[]{id})
                .stream().findAny().orElse(null);
    }

    public void delete(int id){
        jdbcTemplate.update("delete from book where id=?", id);
    }
}
