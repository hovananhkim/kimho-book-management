package com.kimho.book.model.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
public class BookUpdate {
    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @URL(protocol = "https")
    private String image;
}
