package com.kimho.book.converter;

import com.kimho.book.converter.base.Converter;
import com.kimho.book.model.entity.User;
import com.kimho.book.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        if (source.getBooks() != null) {
            userDto.setBooks(bookToDto.convert(source.getBooks()));
        }
        if (source.getComments() != null) {
            userDto.setComments(commentToDto.convert(source.getComments()));
        }
        return userDto;
    }
}
