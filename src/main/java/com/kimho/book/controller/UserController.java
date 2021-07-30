package com.kimho.book.controller;

import com.kimho.book.model.dao.User;
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
    public UserDto put(@Valid @RequestBody UserUpdate user, @PathVariable long id) {
        return userService.put(user, id);
    }

    @PutMapping("/{id}/security")
    public UserDto changPassword(@Valid @RequestBody Password password, @PathVariable long id){
        return userService.changePassword(password, id);
    }

    @PutMapping("/{id}/reset-password")
    public UserDto resetPassword(@PathVariable long id){
        return userService.adminResetPassword(id);
    }

    @PutMapping("/{id}/upgrade")
    public UserDto upgradeAdmin(@PathVariable long id) {
        return userService.upgradeAdmin(id);
    }

    @PutMapping("/{id}/downgrade")
    public UserDto downgradeUser(@PathVariable long id) {
        return userService.downgradeUser(id);
    }

    @PutMapping("/{id}/enable")
    public UserDto enable(@PathVariable long id) {
        return userService.enable(id);
    }
}
