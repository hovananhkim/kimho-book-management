package com.kimho.book.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private long id;

    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String message;

    private long userId;

    @NotNull
    private long bookId;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createdAt = new Date();

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date updatedAt = new Date();
}
