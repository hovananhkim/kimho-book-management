package com.kimho.book.model.secutiry;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
public class JwtRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
