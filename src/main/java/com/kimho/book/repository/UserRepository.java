package com.kimho.book.repository;

import com.kimho.book.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Boolean existsByEmail(String email);

    List<User> findByEmailContainsOrFirstNameContainsOrLastNameContains(String email, String firstName, String lastName);
}
