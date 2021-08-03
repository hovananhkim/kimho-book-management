package com.kimho.book.repository;

import com.kimho.book.model.dao.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainsOrAuthorContains(String title, String author);
}
