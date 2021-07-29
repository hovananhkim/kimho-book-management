package com.kimho.book.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Getter
@Setter
public class CommentDto {
    private long id;

    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String message;

    @NotNull
    private Long userId;

    @NotNull
    private long bookId;

    @NotNull
    private Date createdAt = new Date();

    @NotNull
    private Date updatedAt = new Date();
}
