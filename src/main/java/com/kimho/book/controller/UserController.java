package com.kimho.book.controller;

import com.kimho.book.model.dto.UserDto;
import com.kimho.book.model.dto.UserUpdate;
import com.kimho.book.model.secutiry.PasswordUpdate;
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

    @GetMapping("/find")
    public UserDto findByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/search")
    public List<UserDto> findByName(@RequestParam String name) {
        return userService.findByName(name);
    }

    @PutMapping("/{id}")
    public UserDto edit(@Valid @RequestBody UserUpdate user, @PathVariable long id) {
        return userService.edit(user, id);
    }

    @PutMapping("/{id}/change-password")
    public UserDto changePassword(@Valid @RequestBody PasswordUpdate password, @PathVariable long id) {
        return userService.changePassword(password, id);
    }

    @PutMapping("/{id}/reset-password")
    public UserDto resetPassword(@PathVariable long id) {
        return userService.adminResetPassword(id);
    }

    @PutMapping("/{id}/set-admin")
    public UserDto setAdmin(@PathVariable long id) {
        return userService.setAdmin(id);
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
