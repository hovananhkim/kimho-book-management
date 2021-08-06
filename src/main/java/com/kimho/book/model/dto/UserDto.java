package com.kimho.book.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private long id;

    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    private String password;

    private String firstName;
    private String lastName;

    private boolean enabled = false;

    @URL(protocol = "http")
    private String avatar;

    private String role;

    @JsonIgnoreProperties("comments")
    private List<BookDto> books;

    @JsonIgnoreProperties("userId")
    private List<CommentDto> comments;
}