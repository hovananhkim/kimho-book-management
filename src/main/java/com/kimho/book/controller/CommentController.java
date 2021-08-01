package com.kimho.book.controller;

import com.kimho.book.model.dto.CommentDto;
import com.kimho.book.model.dto.CommentUpdate;
import com.kimho.book.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping
    public List<CommentDto> getAll() {
        return commentService.getAll();
    }

    @GetMapping("/{id}")
    public CommentDto get(@PathVariable long id) {
        return commentService.get(id);
    }

    @PostMapping
    public CommentDto add(@Valid @RequestBody CommentDto bookDto) {
        return commentService.post(bookDto);
    }

    @PutMapping("/{id}")
    public CommentDto edit(@Valid @RequestBody CommentUpdate comment, @PathVariable long id) {
        return commentService.put(comment, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        commentService.delete(id);
    }
}