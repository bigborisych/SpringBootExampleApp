package ru.vladborisov.boot.springbootjmapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.vladborisov.boot.springbootjmapp.model.Role;
import ru.vladborisov.boot.springbootjmapp.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoHiberEntityManager implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    public List<User> getUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void updateUser(User user, Long id) {
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User oldUser = entityManager.createQuery("from User user where user.id=:id", User.class)
                .setParameter("id", id)
                .getSingleResult();
        if (oldUser.getId().equals(id)) {
            user.setRoles(oldUser.getRoles());
            entityManager.merge(user);
        }
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.createQuery("delete from User user where user.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Set<Role> getRoles() {
        return new HashSet<>(entityManager.createQuery("from Role", Role.class).getResultList());
    }

    @Override
    public User findByUsername(String username) {
        return getUsers()
                .stream()
                .filter(x -> x.getUsername().contains(username))
                .findFirst()
                .orElse(null);
    }
}
