package ru.vladborisov.boot.springbootjmapp.dao;

import ru.vladborisov.boot.springbootjmapp.model.Role;
import ru.vladborisov.boot.springbootjmapp.model.User;

import java.util.List;
import java.util.Set;


public interface UserDao {
    void add(User user);

    List<User> getUsers();

    Role getRole(String role);
    void updateUser(User user, Long id);

    void deleteUser(Long id);

    Set<Role> getRoles();

    User findByUsername(String username);
}
