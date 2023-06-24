package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UsersRepository usersRepository;

    private final UserDao userDao;

    public UserServiceImpl(UsersRepository usersRepository, UserDaoImpl userDao) {
        this.usersRepository = usersRepository;
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User showUser(long id) {
        return userDao.showUser(id);
    }

    @Transactional
    @Override
    public void updateUser(User updateUser) {
        userDao.updateUser(updateUser);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getListUsers() {
        return userDao.getListUsers();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.loadUserByUsername(username);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
