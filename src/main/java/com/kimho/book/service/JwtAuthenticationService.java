package com.kimho.book.service;

import com.kimho.book.model.secutiry.JwtRequest;
import com.kimho.book.model.secutiry.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface JwtAuthenticationService {
    ResponseEntity<JwtResponse> login(JwtRequest login);
}
