package com.kimho.book.repository;

import com.kimho.book.model.dao.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
