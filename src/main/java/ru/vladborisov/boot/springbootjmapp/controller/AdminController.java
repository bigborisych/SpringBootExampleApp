package ru.vladborisov.boot.springbootjmapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.vladborisov.boot.springbootjmapp.model.User;
import ru.vladborisov.boot.springbootjmapp.service.UserService;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String printUsers(ModelMap model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("user", new User());
        return "admin_panel";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user, @RequestParam(value = "role") String role) {
        user.setRoles(userService
                .getRoles()
                .stream()
                .filter(x -> x.getRole().contains(role))
                .collect(Collectors.toSet()));
        userService.add(user);
        return "redirect:/admin";
    }

    @PatchMapping
    public String updateUser(@ModelAttribute("user") User user, @RequestParam Long id) {
        userService.updateUser(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping
    public String delete(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}