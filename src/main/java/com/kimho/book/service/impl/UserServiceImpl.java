package com.kimho.book.service.impl;

import com.kimho.book.converter.UserToDto;
import com.kimho.book.exception.BadRequestException;
import com.kimho.book.exception.NotFoundException;
import com.kimho.book.exception.UnauthorizedException;
import com.kimho.book.model.dao.User;
import com.kimho.book.model.dto.UserDto;
import com.kimho.book.model.dto.UserUpdate;
import com.kimho.book.model.secutiry.Password;
import com.kimho.book.repository.RoleRepository;
import com.kimho.book.repository.UserRepository;
import com.kimho.book.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements BooksService<UserDto, UserUpdate> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserToDto userToDto;

    @Override
    public List<UserDto> getAll() {
        return userToDto.convert(userRepository.findAll());
    }

    @Override
    public UserDto get(long id) {
        verifyUserIsExist(id);
        return userToDto.convert(userRepository.findById(id).get());
    }

    public User findById(long id) {
        verifyUserIsExist(id);
        return userRepository.findById(id).get();
    }

    @Override
    public UserDto post(UserDto userDto) {
        verifyUserIsExist(userDto.getEmail());
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAvatar(userDto.getAvatar());
        user.setRole(roleRepository.findByName("ROLE_USER"));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userToDto.convert(userRepository.save(user));
    }

    @Override
    public UserDto put(UserUpdate userEdition, long id) {

        User user = findById(id);
        user.setFirstName(userEdition.getFirstName());
        user.setLastName(userEdition.getLastName());
        user.setAvatar(userEdition.getAvatar());
        return userToDto.convert(userRepository.save(user));
    }

    @Override
    public void delete(long id) {
    }

    public User enable(long id) {
        User user = findById(id);
        User myUser = getMyUser();
        if (user.isSuperAdmin()) {
            throw new UnauthorizedException("Unauthorized");
        } else if (myUser.isAdmin() && user.isUser() || myUser.isSuperAdmin()) {
            user.setEnabled(!user.isEnabled());
            return userRepository.save(user);
        }
        throw new UnauthorizedException("Unauthorized");
    }

    public User upgradeAdmin(long id) {
        User user = findById(id);
        User myUser = getMyUser();
        if (myUser.isSuperAdmin()) {
            user.setRole(roleRepository.findByName("ROLE_ADMIN"));
            return userRepository.save(user);
        } else {
            throw new UnauthorizedException("Unauthorized");
        }
    }

    public User downgradeUser(long id) {
        User user = findById(id);
        User myUser = getMyUser();
        if (myUser.isSuperAdmin()) {
            user.setRole(roleRepository.findByName("ROLE_USER"));
            return userRepository.save(user);
        }
        throw new UnauthorizedException("Unauthorized");
    }

    public User changePassword(Password password, long id) {
        User user = findById(id);
        checkAuthorization(user);
        if (!password.getNewPassword().equals(password.getPrePassword())) {
            throw new BadRequestException("Password is invalid");
        }
        boolean result = passwordEncoder.matches(password.getPassword(), user.getPassword());
        if (result) {
            user.setPassword(new BCryptPasswordEncoder().encode(password.getNewPassword()));
            return userRepository.save(user);
        } else {
            throw new BadRequestException("Password incorrect");
        }
    }

    public User getMyUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername());
    }

    public void checkAuthorization(User user) {
        User myUser = getMyUser();
        if (!(myUser.getId() == user.getId() || myUser.isSuperAdmin() || (myUser.isAdmin() && user.isUser()))) {
            throw new UnauthorizedException("Unauthorized");
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