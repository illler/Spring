package org.crud.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Book {

    private int id;
    @NotEmpty(message = "Book should have a name!")
    private String title;
    private String author;

    @Max(value = 2022, message = "Книга не может быть в будущем!")
    private int year;


    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(){}

    public int geId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
