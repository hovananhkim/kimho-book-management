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
    public UserDto add(UserDto userDto) {
        verifyUserIsExist(userDto.getEmail());
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAvatar(userDto.getAvatar());
        user.setRole(roleRepository.findByName("ROLE_USER"));
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        return userToDto.convert(userRepository.save(user));
    }

    @Override
    public UserDto edit(UserUpdate userEdition, long id) {
        User user = findById(id);
        checkAuthorization(user);
        user.setFirstName(userEdition.getFirstName());
        user.setLastName(userEdition.getLastName());
        user.setAvatar(userEdition.getAvatar());
        return userToDto.convert(userRepository.save(user));
    }

    @Override
    public void delete(long id) {
    }

    public UserDto enable(long id) {
        User user = findById(id);
        User myUser = getMyUser();
        if (user.isSuperAdmin()) {
            throw new UnauthorizedException("Unauthorized");
        } else if (myUser.isAdmin() && user.isUser() || myUser.isSuperAdmin()) {
            user.setEnabled(!user.isEnabled());
            return userToDto.convert(userRepository.save(user));
        }
        throw new UnauthorizedException("Unauthorized");
    }

    public UserDto upgradeAdmin(long id) {
        User user = findById(id);
        User myUser = getMyUser();
        if (myUser.isSuperAdmin()) {
            user.setRole(roleRepository.findByName("ROLE_ADMIN"));
            user.setEnabled(true);
            return userToDto.convert(userRepository.save(user));
        }
        throw new UnauthorizedException("Unauthorized");
    }

    public UserDto removeAdmin(long id) {
        User user = findById(id);
        User myUser = getMyUser();
        if (myUser.isSuperAdmin()) {
            user.setRole(roleRepository.findByName("ROLE_USER"));
            return userToDto.convert(userRepository.save(user));
        }
        throw new UnauthorizedException("Unauthorized");
    }

    public UserDto changePassword(Password password, long id) {
        User user = findById(id);
        User myUser = getMyUser();
        if (!(myUser.getId()==user.getId())){
            throw new UnauthorizedException("Unauthorized");
        }
        boolean result = passwordEncoder.matches(password.getPassword(), user.getPassword());
        if (result) {
            user.setPassword(new BCryptPasswordEncoder().encode(password.getNewPassword()));
            return userToDto.convert(userRepository.save(user));
        } else {
            throw new BadRequestException("Password incorrect");
        }
    }
    public UserDto adminResetPassword(long id){
        User user = findById(id);
        User myUser = getMyUser();
        if (myUser.isSuperAdmin() || (myUser.isAdmin()) && user.isUser()){
            user.setPassword("password");
            return userToDto.convert(userRepository.save(user));
        }
        throw new UnauthorizedException("Unauthorized");
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