package com.kimho.book.service.impl;

import com.kimho.book.converter.CommentToDto;
import com.kimho.book.converter.DtoToComment;
import com.kimho.book.model.dao.Comment;
import com.kimho.book.model.dto.CommentDto;
import com.kimho.book.model.dto.CommentUpdate;
import com.kimho.book.repository.CommentRepository;
import com.kimho.book.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements BooksService<CommentDto, CommentUpdate> {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentToDto commentToDto;
    @Autowired
    private DtoToComment dtoToComment;

    @Override
    public List<CommentDto> getAll() {
        return commentToDto.convert(commentRepository.findAll());
    }

    @Override
    public CommentDto get(long id) {
        return commentToDto.convert(commentRepository.findById(id).get());
    }

    @Override
    public CommentDto post(CommentDto commentDto) {
        return commentToDto.convert(commentRepository.save(dtoToComment.convert(commentDto)));
    }

    @Override
    public CommentDto put(CommentUpdate commentEdition, long id) {
        Comment comment = commentRepository.findById(id).get();
        comment.setMessage(commentEdition.getMessage());
        comment.setUpdatedAt(new Date());
        return commentToDto.convert(comment);
    }

    @Override
    public void delete(long id) {
        commentRepository.deleteById(id);
    }
}