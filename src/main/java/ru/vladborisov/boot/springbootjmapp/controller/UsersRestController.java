package ru.vladborisov.boot.springbootjmapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import ru.vladborisov.boot.springbootjmapp.model.Role;
import ru.vladborisov.boot.springbootjmapp.model.User;
import ru.vladborisov.boot.springbootjmapp.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UsersRestController {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<Set<Role>> getRoles() {
        return new ResponseEntity<>(userService.getRoles(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.add(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateUser(user, user.getId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<List<String>> deleteUser(@PathVariable Long id) {
        List<String> list = new ArrayList<>();
        list.add("User with ID: " + id + " was deleted");
        userService.deleteUser(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
