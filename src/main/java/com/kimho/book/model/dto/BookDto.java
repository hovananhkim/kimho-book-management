package com.kimho.book.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {
    private long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createdAt = new Date();

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date updatedAt = new Date();

    @URL(protocol = "http")
    private String image;

    private boolean enabled = false;

    @NotNull
    private long userId;

    @JsonIgnoreProperties("bookId")
    private List<CommentDto> comments;
}
