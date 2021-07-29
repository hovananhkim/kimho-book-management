package com.kimho.book.controller;

import com.kimho.book.model.dao.User;
import com.kimho.book.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JwtAuthenticationController {
    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/register")
    public User post(@RequestBody User user) {
        return userService.post(user);
    }
}
