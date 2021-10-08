package com.kimho.book.converter;

import com.kimho.book.converter.base.Converter;
import com.kimho.book.model.entity.Comment;
import com.kimho.book.model.dto.CommentDto;
import com.kimho.book.service.impl.BookServiceImpl;
import com.kimho.book.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoToComment extends Converter<CommentDto, Comment> {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BookServiceImpl bookService;

    @Override
    public Comment convert(CommentDto source) {
        Comment comment = new Comment();
        comment.setId(source.getId());
        comment.setMessage(source.getMessage());
        comment.setBook(bookService.findById(source.getBookId()));
        comment.setUser(userService.findById(source.getUserId()));
        return comment;
    }
}
