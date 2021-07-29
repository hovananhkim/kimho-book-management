package com.kimho.book.converter;

import com.kimho.book.converter.base.Converter;
import com.kimho.book.model.dao.Book;
import com.kimho.book.model.dto.BookDto;
import com.kimho.book.repository.UserRepository;
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
        book.setUser(userService.get(source.getUserId()));
        book.setImage(source.getImage());
        book.setCreatedAt(source.getCreatedAt());
        book.setUpdatedAt(source.getUpdatedAt());

        return book;
    }
}
