package com.kimho.book.service;

import com.kimho.book.model.entity.Comment;
import com.kimho.book.model.dto.CommentDto;
import com.kimho.book.model.dto.CommentUpdate;

public interface CommentService {

    CommentDto get(long id);

    Comment findById(long id);

    public CommentDto add(CommentDto commentDto);

    CommentDto edit(CommentUpdate commentEdition, long id);

    void delete(long id);
}
