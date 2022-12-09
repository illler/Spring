package org.crud.controllers;

import org.crud.dao.BookDAO;
import org.crud.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-batch")
public class BatchController {

    private final PersonDAO personDAO;

    private final BookDAO bookDAO;

    @Autowired
    public BatchController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String index(){
        return "batch/index";
    }

    @GetMapping("/people")
    public String peopleBatch(){
        personDAO.testBatchUpdate();
        return "redirect:/library/people";
    }

    @GetMapping("/book")
    public String bookBatch(){
        bookDAO.testBatchUpdate();
        return "redirect:/library/book";
    }
}
