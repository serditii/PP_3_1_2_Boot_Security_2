package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {

    void addUser(User user);

    void deleteUser(long id);

    User showUser(long id);

    void updateUser(User updateUser);

    List<User> getListUsers();

    User findByUsername(String username);
}
