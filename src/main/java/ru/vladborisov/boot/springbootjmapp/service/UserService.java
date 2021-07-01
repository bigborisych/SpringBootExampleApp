package ru.vladborisov.boot.springbootjmapp.service;

import ru.vladborisov.boot.springbootjmapp.model.Role;
import ru.vladborisov.boot.springbootjmapp.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void add(User user);

    List<User> getUsers();

    void updateUser(User user, Long id);

    void deleteUser(Long id);

    Set<Role> getRoles();
}
