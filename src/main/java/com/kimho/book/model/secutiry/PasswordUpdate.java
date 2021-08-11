package com.kimho.book.model.secutiry;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PasswordUpdate {
    @NotBlank
    private String password;
    @NotBlank
    private String newPassword;
}
