package com.kimho.book.model.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
public class UserUpdate {
    private String firstName;
    private String lastName;

    @URL(protocol = "http")
    private String avatar;
}
