package org.crud.controllers;

import org.crud.dao.BookDAO;
import org.crud.models.Book;
import org.crud.models.Person;
import org.crud.util.BookValidator;
import org.crud.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/library/book")
public class BookController {

    private final BookDAO bookDAO;

    private final BookValidator bookValidator;


    @Autowired
    public BookController(BookDAO bookDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }


    @GetMapping
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("books", bookDAO.show(id));
        return "book/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("books") Book book){
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("books") @Valid Book book,
                         BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()){
            return "book/new";
        }
        bookDAO.save(book);
        return "redirect:/library/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("books", bookDAO.show(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("books") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id){
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()){
            return "book/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/library/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/library/book";
    }


}
