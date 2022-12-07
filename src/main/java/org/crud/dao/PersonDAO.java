package org.crud.dao;

import org.crud.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return jdbcTemplate.query("SELECT * from Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person (fullname, year_of_birth) VALUES(?, ?)",
                person.getFullname(), person.getYear_of_birth());
    }

    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE Person SET fullname=?, year_of_birth=? where person_id=?",
                person.getFullname(), person.getYear_of_birth(), id);
    }

    public Optional<Person> show(String email){
        return jdbcTemplate.query("Select * from Person where email=?", new BeanPropertyRowMapper<>(Person.class), new Object[]{email})
                .stream().findAny();
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE from Person where person_id=?", id);
    }

    public Person show(int id){
        return jdbcTemplate.query("select * from Person where person_id=?", new BeanPropertyRowMapper<>(Person.class), new Object[]{id})
                .stream().findAny().orElse(null);
    }

    public boolean check(int id){
        return jdbcTemplate.query("select id from book join person p on p.person_id = book.person_id where p.person_id=?",
                new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().isPresent();
    }

    public void deleteAll(){
        jdbcTemplate.update("delete from Person where person_id>0");
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void testMultipleUpdate(){
        List<Person> people = create1000people();
        long before = System.currentTimeMillis();
        for (Person person:people) {
            jdbcTemplate.update("INSERT INTO Person (fullname, year_of_birth) VALUES(?, ?)",
                    person.getFullname(), person.getYear_of_birth());
        }
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after-before));
    }

    public void testBatchUpdate(){
        List<Person> people = create1000people();
        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("insert into Person(fullname, year_of_birth) values (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setString(1, people.get(i).getFullname());
                        preparedStatement.setInt(2, people.get(i).getYear_of_birth());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after-before));
    }

    public List<Person> create1000people(){
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            people.add(new Person(i, "FirstName" + i, 2000));
        }
        return people;
    }
}
