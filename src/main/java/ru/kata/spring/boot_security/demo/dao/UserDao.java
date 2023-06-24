package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    void addUser(User user);

    void deleteUser(long id);

    User showUser(long id);

    Optional<User> showUser(String email);

    void updateUser(User updateUser);

    @SuppressWarnings("unchecked")
    List<User> getListUsers();

    UserDetails loadUserByUsername(String username);

    User findByUsername(String username);
}
