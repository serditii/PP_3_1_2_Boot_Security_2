package ru.kata.spring.boot_security.demo.dao;

import org.hibernate.query.Query;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final RolesRepository roleRepository;

    private final UsersRepository usersRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Lazy
    public UserDaoImpl(RolesRepository roleRepository,
                       UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void addUser(User user) {
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    public void updateUser(User updateUser) {
        updateUser.setRoles(showUser(updateUser.getId()).getRoles());
        entityManager.merge(updateUser);
    }

    @Override
    public void deleteUser(long id) {
        if (usersRepository.findById(id).isPresent()) {
            usersRepository.deleteById(id);
        }
    }

    @Override
    public Optional<User> showUser(String email) {
        Query<User> query = (Query<User>) entityManager
                .createQuery("from User where email = :name ")
                .setParameter("name", email);
        List<User> list = query.getResultList();
        if (list == null) {
            return null;
        }
        return list.stream().findAny();
    }

    @Override
    public User showUser(long id) {
        Optional<User> userFromDb = usersRepository.findById(id);
        return userFromDb.orElse(new User());
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> getListUsers() {
        return usersRepository.findAll();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String
                    .format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getAuthorities());
    }

    @Override
    public User findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}

