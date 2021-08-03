package com.kimho.book.controller;

import com.kimho.book.model.dto.UserDto;
import com.kimho.book.model.dto.UserUpdate;
import com.kimho.book.model.secutiry.Password;
import com.kimho.book.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable long id) {
        return userService.get(id);
    }

    @PutMapping("/{id}")
    public UserDto edit(@Valid @RequestBody UserUpdate user, @PathVariable long id) {
        return userService.edit(user, id);
    }

    @PutMapping("/{id}/security")
    public UserDto changePassword(@Valid @RequestBody Password password, @PathVariable long id) {
        return userService.changePassword(password, id);
    }

    @PutMapping("/{id}/reset-password")
    public UserDto resetPassword(@PathVariable long id) {
        return userService.adminResetPassword(id);
    }

    @PutMapping("/{id}/set-admin")
    public UserDto upgradeAdmin(@PathVariable long id) {
        return userService.upgradeAdmin(id);
    }

    @PutMapping("/{id}/remove-admin")
    public UserDto removeAdmin(@PathVariable long id) {
        return userService.removeAdmin(id);
    }

    @PutMapping("/{id}/enable")
    public UserDto enable(@PathVariable long id) {
        return userService.enable(id);
    }
}
