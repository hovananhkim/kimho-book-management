package com.kimho.book.exception;

import com.kimho.book.model.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.lang.module.ResolutionException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseException handlerNotFoundException(NotFoundException e){
        ResponseException re = new ResponseException(404, "Not Found", e.getMessage());
        return re;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseException handlerBadRequestException(BadRequestException e){
        ResponseException re = new ResponseException(400, "Bad Request", e.getMessage());
        return re;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseException handlerUnauthorizedException(UnauthorizedException e){
        ResponseException re = new ResponseException(401, "Unauthorized", e.getMessage());
        return re;
    }

}
