package com.kimho.book.controller;

import com.kimho.book.model.dto.BookDto;
import com.kimho.book.model.dto.BookUpdate;
import com.kimho.book.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public BookDto add(@Valid @RequestBody BookDto bookDto) {
        return bookService.add(bookDto);
    }

    @PutMapping("/{id}")
    public BookDto edit(@Valid @RequestBody BookUpdate book, @PathVariable long id) {
        return bookService.edit(book, id);
    }

    @PutMapping("/{id}/enable")
    public BookDto enable(@PathVariable long id) {
        return bookService.enable(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        bookService.delete(id);
    }

    @GetMapping("/search")
    public List<BookDto> findByTitle(@RequestParam String keyword){
        return bookService.findByTitleOrAuthor(keyword);
    }
}
