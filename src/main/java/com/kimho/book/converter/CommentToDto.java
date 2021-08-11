package com.kimho.book.converter;

import com.kimho.book.converter.base.Converter;
import com.kimho.book.model.dao.Comment;
import com.kimho.book.model.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentToDto extends Converter<Comment, CommentDto> {
    @Override
    public CommentDto convert(Comment source) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(source.getId());
        commentDto.setMessage(source.getMessage());
        commentDto.setBookId(source.getBook().getId());
        commentDto.setUserId(source.getUser().getId());
        commentDto.setCreatedAt(source.getCreatedAt());
        commentDto.setUpdatedAt(source.getUpdatedAt());
        return commentDto;
    }
}
