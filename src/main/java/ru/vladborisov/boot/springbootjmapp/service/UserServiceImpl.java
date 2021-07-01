package ru.vladborisov.boot.springbootjmapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vladborisov.boot.springbootjmapp.dao.UserDao;
import ru.vladborisov.boot.springbootjmapp.model.Role;
import ru.vladborisov.boot.springbootjmapp.model.User;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public void updateUser(User user, Long id) {
        userDao.updateUser(user, id);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public Set<Role> getRoles() {
        return userDao.getRoles();
    }
}
