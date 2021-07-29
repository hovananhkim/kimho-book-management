package com.kimho.book.controller;

import com.kimho.book.model.dao.User;
import com.kimho.book.model.secutiry.Password;
import com.kimho.book.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        return userService.get(id);
    }

    @PutMapping("/{id}")
    public User put(@RequestBody User user,@PathVariable long id) {
        return userService.put(user, id);
    }

    @PutMapping("/{id}/security")
    public User changPassword(@RequestBody Password password, @PathVariable long id){
        return userService.changePassword(password, id);
    }

    @PutMapping("/{id}/upgrade")
    public User upgradeAdmin(@PathVariable long id) {
        return userService.upgradeAdmin(id);
    }

    @PutMapping("/{id}/downgrade")
    public User downgradeUser(@PathVariable long id) {
        return userService.downgradeUser(id);
    }

    @PutMapping("/{id}/enable")
    public User enable(@PathVariable long id) {
        return userService.enable(id);
    }
}
