package com.kimho.book.converter;

import com.kimho.book.converter.base.Converter;
import com.kimho.book.model.entity.Book;
import com.kimho.book.model.dto.BookDto;
import com.kimho.book.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoToBook extends Converter<BookDto, Book> {
    @Autowired
    private UserServiceImpl userService;

    public Book convert(BookDto source) {
        Book book = new Book();
        book.setId(source.getId());
        book.setTitle(source.getTitle());
        book.setAuthor(source.getAuthor());
        book.setDescription(source.getDescription());
        book.setEnabled(source.isEnabled());
        book.setUser(userService.findById(source.getUserId()));
        book.setImage(source.getImage());
        return book;
    }
}
