package com.kimho.book.service;

import com.kimho.book.model.dao.Book;
import com.kimho.book.model.dto.BookDto;
import com.kimho.book.model.dto.BookUpdate;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    BookDto get(long id);

    Book findById(long id);

    BookDto add(BookDto bookDto);

    BookDto edit(BookUpdate bookEdition, long id);

    void delete(long id) ;

    List<BookDto> findByTitleOrAuthor(String keyword);

    BookDto enable(long id);

    boolean checkAuthorizationEditBook(Book book);

    boolean checkAuthorizationEnableBook(Book book);

    void verifyBookIsExist(long id);
}
