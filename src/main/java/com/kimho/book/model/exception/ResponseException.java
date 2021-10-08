package com.kimho.book.model.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class ResponseException {
    private Date timestamp;
    private int status;
    private String error;
    private String message;

    public ResponseException(int status, String error, String message) {
        this.timestamp = new Date();
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
