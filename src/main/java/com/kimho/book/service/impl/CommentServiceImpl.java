package com.kimho.book.service.impl;

import com.kimho.book.converter.CommentToDto;
import com.kimho.book.converter.DtoToComment;
import com.kimho.book.exception.NotFoundException;
import com.kimho.book.exception.UnauthorizedException;
import com.kimho.book.model.dao.Comment;
import com.kimho.book.model.dao.User;
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

    @Autowired
    private UserServiceImpl userService;

    @Override
    public List<CommentDto> getAll() {
        return null;
    }

    @Override
    public CommentDto get(long id)  {
        return commentToDto.convert(findById(id));
    }

    private Comment findById(long id) {
        verifyCommentIsExist(id);
        return commentRepository.findById(id).get();
    }

    @Override
    public CommentDto add(CommentDto commentDto) {
        commentDto.setUserId(userService.getMyUser().getId());
        Comment comment = dtoToComment.convert(commentDto);
        return commentToDto.convert(commentRepository.save(comment));
    }

    @Override
    public CommentDto edit(CommentUpdate commentEdition, long id) {
        Comment comment = findById(id);
        if (!checkAuthorized(comment.getUser())) {
            throw new UnauthorizedException("Unauthorized");
        }
        comment.setMessage(commentEdition.getMessage());
        comment.setUpdatedAt(new Date());
        return commentToDto.convert(comment);
    }

    @Override
    public void delete(long id) {
        verifyCommentIsExist(id);
        if (!checkAuthorized(findById(id).getUser())) {
            throw new UnauthorizedException("Unauthorized");
        }
        commentRepository.deleteById(id);
    }

    private boolean checkAuthorized(User userCreateComment) {
        User myUser = userService.getMyUser();
        if (myUser.getId() == userCreateComment.getId()) {
            return true;
        }
        return false;
    }

    private void verifyCommentIsExist(long id) {
        if (!commentRepository.existsById(id)) {
            throw new NotFoundException(String.format("Comment id: %d not found", id));
        }
    }
}