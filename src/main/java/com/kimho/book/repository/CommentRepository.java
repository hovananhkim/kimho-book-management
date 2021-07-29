package com.kimho.book.repository;

import com.kimho.book.model.dao.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
