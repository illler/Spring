package org.crud.controllers;

import org.crud.dao.BookDAO;
import org.crud.dao.PersonDAO;
import org.crud.models.Book;
import org.crud.models.Person;
import org.crud.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/library/book")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    private final BookValidator bookValidator;


    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
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
        model.addAttribute("check", bookDAO.check(id));
        model.addAttribute("book_busy", bookDAO.book_busy(id));
        model.addAttribute("people", personDAO.index());
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

    @PatchMapping("/{id}/del")
    public String resque_book(@PathVariable("id") int id){
        bookDAO.resque(id);
        return "redirect:/library/book/" + id;
    }
    @PatchMapping("/{id}/upd")
    public String chooseBook(@ModelAttribute("people") Person person, @PathVariable("id") int id,
                             @Valid int people){
        bookDAO.append(people, id);
        return "redirect:/library/book/" + id;
    }
    
}
