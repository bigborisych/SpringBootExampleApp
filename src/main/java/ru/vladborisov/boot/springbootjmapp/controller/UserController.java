package ru.vladborisov.boot.springbootjmapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserDetailsService userService;

    @Autowired
    public UserController(UserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String printUserInfo(Principal principal, Model model) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "user_info";
    }
}
