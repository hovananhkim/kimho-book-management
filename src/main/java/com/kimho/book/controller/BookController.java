package com.kimho.book.controller;

import com.kimho.book.model.dao.User;
import com.kimho.book.model.dto.BookDto;
import com.kimho.book.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookServiceImpl bookService;
    @GetMapping
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public BookDto get(@PathVariable long id) {
        return bookService.get(id);
    }

    @PostMapping
    public BookDto post(@RequestBody BookDto bookDto){
        return bookService.post(bookDto);
    }
}
