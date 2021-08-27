package com.kimho.book.service;

import com.kimho.book.model.dao.User;
import com.kimho.book.model.dto.UserDto;
import com.kimho.book.model.dto.UserUpdate;
import com.kimho.book.model.secutiry.PasswordUpdate;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    UserDto get(long id);

    User findById(long id);

    UserDto add(UserDto userDto);

    UserDto edit(UserUpdate userEdition, long id);

    UserDto findByEmail(String email);

    List<UserDto> findByName(String name);

    UserDto enable(long id);

    UserDto setAdmin(long id);

    UserDto removeAdmin(long id);

    UserDto changePassword(PasswordUpdate password, long id);

    UserDto adminResetPassword(long id);

    User getMyUser();
}
