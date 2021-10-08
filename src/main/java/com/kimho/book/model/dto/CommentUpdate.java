package com.kimho.book.model.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CommentUpdate {
    @NotEmpty
    private String message;
}
