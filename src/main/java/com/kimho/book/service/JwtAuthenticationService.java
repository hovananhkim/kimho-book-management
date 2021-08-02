package com.kimho.book.service;

import com.kimho.book.config.JwtTokenProvider;
import com.kimho.book.model.secutiry.JwtRequest;
import com.kimho.book.model.secutiry.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationService {
    @Autowired

    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<JwtResponse> login(JwtRequest login) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}