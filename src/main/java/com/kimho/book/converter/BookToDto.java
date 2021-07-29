package com.kimho.book.converter;

import com.kimho.book.converter.base.Converter;
import com.kimho.book.model.dao.Book;
import com.kimho.book.model.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookToDto extends Converter<Book, BookDto> {
    @Autowired
    private CommentToDto commentToDto;

    @Override
    public BookDto convert(Book source) {
        BookDto bookDto = new BookDto();
        bookDto.setId(source.getId());
        bookDto.setTitle(source.getTitle());
        bookDto.setAuthor(source.getAuthor());
        bookDto.setDescription(source.getDescription());
        bookDto.setEnabled(source.isEnabled());
        bookDto.setUserId(source.getUser().getId());
        if (source.getComments() != null) {
            bookDto.setComments(commentToDto.convert(source.getComments()));
        }
        bookDto.setImage(source.getImage());
        bookDto.setCreatedAt(source.getCreatedAt());
        bookDto.setUpdatedAt(source.getUpdatedAt());

        return bookDto;
    }
}
