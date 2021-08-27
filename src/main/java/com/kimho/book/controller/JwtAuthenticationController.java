package com.kimho.book.controller;

import com.kimho.book.model.dto.UserDto;
import com.kimho.book.model.secutiry.JwtRequest;
import com.kimho.book.model.secutiry.JwtResponse;
import com.kimho.book.service.impl.JwtAuthenticationServiceImpl;
import com.kimho.book.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class JwtAuthenticationController {
    @Autowired
    private JwtAuthenticationServiceImpl jwtAuthenticationService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest login) {
        return jwtAuthenticationService.login(login);
    }

    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody UserDto user) {
        return userService.add(user);
    }
}
