package org.crud.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class Book {

    private int id;
    @NotEmpty(message = "Book should have a name!")
    private String title;
    @Pattern(regexp = "([A-ZА-ЯЁ][a-zа-яё]+ [A-ZА-ЯЁ].[A-ZА-ЯЁ].|Нет автора)",
            message = "ФИО автора должно быть в формате: Иванов И.И. или 'Нет автора'")
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
