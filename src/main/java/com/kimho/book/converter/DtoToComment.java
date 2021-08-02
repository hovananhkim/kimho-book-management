package com.kimho.book.converter;

import com.kimho.book.converter.base.Converter;
import com.kimho.book.exception.NotFoundException;
import com.kimho.book.model.dao.Comment;
import com.kimho.book.model.dto.CommentDto;
import com.kimho.book.repository.BookRepository;
import com.kimho.book.service.impl.BookServiceImpl;
import com.kimho.book.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoToComment extends Converter<CommentDto, Comment> {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Comment convert(CommentDto source) {
        Comment comment = new Comment();
        comment.setId(source.getId());
        comment.setMessage(source.getMessage());
        if (bookRepository.existsById(source.getBookId())) {
            comment.setBook(bookRepository.findById(source.getBookId()).get());
        } else {
            throw new NotFoundException("Book not found");
        }
        comment.setUser(userService.findById(source.getUserId()));
        return comment;
    }
}
