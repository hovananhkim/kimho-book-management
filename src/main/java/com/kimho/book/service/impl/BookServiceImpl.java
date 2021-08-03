package com.kimho.book.service.impl;

import com.kimho.book.converter.BookToDto;
import com.kimho.book.converter.DtoToBook;
import com.kimho.book.exception.NotFoundException;
import com.kimho.book.exception.UnauthorizedException;
import com.kimho.book.model.dao.Book;
import com.kimho.book.model.dao.User;
import com.kimho.book.model.dto.BookDto;
import com.kimho.book.model.dto.BookUpdate;
import com.kimho.book.repository.BookRepository;
import com.kimho.book.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BooksService<BookDto, BookUpdate> {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookToDto bookToDto;

    @Autowired
    private DtoToBook dtoToBook;

    @Autowired
    private UserServiceImpl userService;

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

    private Book findById(long id) {
        verifyBookIsExist(id);
        return bookRepository.findById(id).get();
    }

    @Override
    public BookDto add(BookDto bookDto) {
        User myUser = userService.getMyUser();
        bookDto.setUserId(userService.getMyUser().getId());
        Book book = dtoToBook.convert(bookDto);
        if (myUser.isAdmin() || myUser.isSuperAdmin()) {
            book.setEnabled(true);
        }
        BookDto bookResponse = bookToDto.convert(bookRepository.save(book));
        return bookResponse;
    }

    @Override
    public BookDto edit(BookUpdate bookEdition, long id) {
        verifyBookIsExist(id);
        Book book = bookRepository.findById(id).get();
        if (!checkAuthorizationEditBook(book)) {
            throw new UnauthorizedException("Unauthorized");
        }
        book.setTitle(bookEdition.getTitle());
        book.setAuthor(bookEdition.getAuthor());
        book.setDescription(bookEdition.getDescription());
        book.setImage(bookEdition.getImage());
        book.setUpdatedAt(new Date());
        return bookToDto.convert(bookRepository.save(book));
    }

    @Override
    public void delete(long id) {
        verifyBookIsExist(id);
        if (!checkAuthorizationEditBook(findById(id))) {
            throw new UnauthorizedException("Unauthorized");
        }
        bookRepository.deleteById(id);
    }

    public List<BookDto> findByTitleOrAuthor(String keyword){
        return bookToDto.convert(bookRepository.findByTitleContainsOrAuthorContains(keyword, keyword));
    }


    public BookDto enable(long id) {
        Book book = bookRepository.getById(id);
        if (!checkAuthorizationEnableBook(book)) {
            throw new UnauthorizedException("Unauthorized");
        }
        book.setEnabled(!book.isEnabled());
        return bookToDto.convert(bookRepository.save(book));
    }

    private boolean checkAuthorizationEditBook(Book book) {
        User myUser = userService.getMyUser();
        User userCreateBook = book.getUser();
        if (userCreateBook.getId() == myUser.getId()) {
            return true;
        } else if (myUser.isSuperAdmin()) {
            return true;
        } else if (userCreateBook.isUser() && myUser.isAdmin()) {
            return true;
        }
        return false;
    }

    private boolean checkAuthorizationEnableBook(Book book) {
        User userCreateBook = book.getUser();
        User myUser = userService.getMyUser();
        if (myUser.isSuperAdmin()) {
            return true;
        } else if (myUser.isAdmin() && (userCreateBook.isUser() || myUser.getId() == userCreateBook.getId())) {
            return true;
        }
        return false;
    }

    public void verifyBookIsExist(long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Book not found");
        }
    }
}
