package ru.vladborisov.boot.springbootjmapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vladborisov.boot.springbootjmapp.model.Role;
import ru.vladborisov.boot.springbootjmapp.model.User;
import ru.vladborisov.boot.springbootjmapp.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class UsersController {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserService userService;

    @GetMapping
    public String getDefaultPage(Model model) {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Set<Role> roles = userService.getRoles();
        List<User> users = userService.getUsers();

        model.addAttribute("users", users);
        model.addAttribute("authUser", user);
        model.addAttribute("roles", roles);
        model.addAttribute("newUser", new User());
        model.addAttribute("patchUser", new User());
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
}
