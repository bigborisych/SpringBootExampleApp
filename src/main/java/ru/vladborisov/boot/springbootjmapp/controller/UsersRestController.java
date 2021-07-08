package ru.vladborisov.boot.springbootjmapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import ru.vladborisov.boot.springbootjmapp.model.Role;
import ru.vladborisov.boot.springbootjmapp.model.User;
import ru.vladborisov.boot.springbootjmapp.service.UserService;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UsersRestController {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }
    @GetMapping(value = "/roles")
    public Set<Role> getRoles() {
        return userService.getRoles();
    }
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        userService.add(user);
        return user;
    }
    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        userService.updateUser(user, user.getId());
        return user;
    }
    @DeleteMapping( "/users/{id}")
    public List<String> deleteUser(@PathVariable Long id) {
        List<String> list = new ArrayList<>();
        list.add("User with ID: " + id + " was deleted");
        userService.deleteUser(id);
        return list;
    }
}
