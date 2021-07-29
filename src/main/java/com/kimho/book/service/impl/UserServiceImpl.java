package com.kimho.book.service.impl;

import com.kimho.book.exception.BadRequestException;
import com.kimho.book.exception.NotFoundException;
import com.kimho.book.model.dao.User;
import com.kimho.book.model.secutiry.Password;
import com.kimho.book.repository.RoleRepository;
import com.kimho.book.repository.UserRepository;
import com.kimho.book.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements BooksService<User> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(long id) {
        verifyUserIsExist(id);
        return userRepository.findById(id).get();
    }

    @Override
    public User post(User user) {
        verifyUserIsExist(user.getEmail());
        user.setRole(roleRepository.findByName("ROLE_USER"));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User put(User user, long id) {
        User userEdition = get(id);
        userEdition.setFirstName(user.getFirstName());
        userEdition.setLastName(user.getLastName());
        return userRepository.save(userEdition);
    }

    @Override
    public void delete(long id) {}

    public User enable(long id) {
        User user = get(id);
        user.setEnabled(!user.isEnabled());
        return userRepository.save(user);
    }

    public User upgradeAdmin(long id) {
        User user = get(id);
        user.setRole(roleRepository.findByName("ROLE_ADMIN"));
        return userRepository.save(user);
    }

    public User downgradeUser(long id) {
        User user = get(id);
        user.setRole(roleRepository.findByName("ROLE_USER"));
        return userRepository.save(user);
    }

    public User changePassword(Password password, long id) {
        User user = get(id);
        if (!password.getNewPassword().equals(password.getPrePassword())) {
            throw new BadRequestException("Password is invalid");
        }
        boolean result = passwordEncoder.matches(password.getPassword(), user.getPassword());
        if (result) {
            user.setPassword(new BCryptPasswordEncoder().encode(password.getNewPassword()));
            return userRepository.save(user);
        }else {
            throw new BadRequestException("Password incorrect");
        }
    }

    public void verifyUserIsExist(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new BadRequestException("Email is existed");
        }
    }

    public void verifyUserIsExist(long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found");
        }
    }
}