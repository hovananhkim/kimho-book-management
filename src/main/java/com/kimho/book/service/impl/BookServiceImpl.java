package com.kimho.book.service.impl;

import com.kimho.book.converter.BookToDto;
import com.kimho.book.converter.DtoToBook;
import com.kimho.book.exception.NotFoundException;
import com.kimho.book.model.dao.Book;
import com.kimho.book.model.dto.BookDto;
import com.kimho.book.repository.BookRepository;
import com.kimho.book.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class BookServiceImpl implements BooksService<BookDto> {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookToDto bookToDto;
    @Autowired
    private DtoToBook dtoToBook;
    @Override
    public List<BookDto> getAll() {
        List<Book> books = bookRepository.findAll();
        return bookToDto.convert(books);
    }

    @Override
    public BookDto get(long id) {
        verifyBookIsExist(id);
        return bookToDto.convert(bookRepository.findById(id).get());
    }

    @Override
    public BookDto post(BookDto bookDto) {
        Book book = dtoToBook.convert(bookDto);
        BookDto bookResponse = bookToDto.convert(bookRepository.save(book));
        return bookResponse;
    }

    @Override
    public BookDto put(BookDto bookDto, long id) {
        verifyBookIsExist(id);
        Book book = bookRepository.findById(id).get();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setDescription(bookDto.getDescription());
        book.setImage(bookDto.getImage());
        book.setUpdatedAt(new Date());
        return bookToDto.convert(bookRepository.save(book));
    }

    @Override
    public void delete(long id) {
        verifyBookIsExist(id);
        bookRepository.deleteById(id);
    }

    public BookDto enabled(long id){
        Book book = bookRepository.getById(id);
        book.setEnabled(!book.isEnabled());
        return bookToDto.convert(bookRepository.save(book));
    }

    public void verifyBookIsExist(long id){
        if (!bookRepository.existsById(id)){
            throw new NotFoundException("Book not found");
        }
    }
}
