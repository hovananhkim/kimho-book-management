package com.kimho.book.converter;

import com.kimho.book.converter.base.Converter;
import com.kimho.book.model.dao.Book;
import com.kimho.book.model.dao.Comment;
import com.kimho.book.model.dao.User;
import com.kimho.book.model.dto.CommentDto;
import com.kimho.book.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserToDto extends Converter<User, UserDto> {
    @Autowired
    private BookToDto bookToDto;
    @Autowired
    private CommentToDto commentToDto;

    @Override
    public UserDto convert(User source) {
        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setEmail(source.getEmail());
        userDto.setFirstName(source.getFirstName());
        userDto.setLastName(source.getLastName());
        userDto.setEnabled(source.isEnabled());
        userDto.setAvatar(source.getAvatar());
        userDto.setRole(source.getRole().getName());
        userDto.setBooks(bookToDto.convert(source.getBooks()));
        userDto.setComments(commentToDto.convert(source.getComments()));
        return userDto;
    }
}
