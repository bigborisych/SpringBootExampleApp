package ru.vladborisov.boot.springbootjmapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vladborisov.boot.springbootjmapp.model.Role;
import ru.vladborisov.boot.springbootjmapp.model.User;
import ru.vladborisov.boot.springbootjmapp.service.UserService;

import java.security.Principal;
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
    public String getDefaultPage(Principal principal, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Set<Role> roles = userService.getRoles();
        List<User> users = userService.getUsers();

        model.addAttribute("users", users);
        model.addAttribute("authUser", user);
        model.addAttribute("roles", roles);
        model.addAttribute("newUser", new User());
        model.addAttribute("patchUser", new User());
        return "index";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user, @RequestParam String[] role) {
        user.setRoles(userService
                .getRoles()
                .stream()
                .filter((x) -> Arrays.stream(role).allMatch(y -> x.getRole().contains(y)))
                .collect(Collectors.toSet()));
        userService.add(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @DeleteMapping
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    @PatchMapping
    public String updateUser(@ModelAttribute User user, @RequestParam Long id, @RequestParam String[] role) {
        user.setRoles(userService
                .getRoles()
                .stream()
                .filter((x) -> Arrays.stream(role).allMatch(y -> x.getRole().contains(y)))
                .collect(Collectors.toSet()));
        user.getRoles().forEach(x -> System.out.println(x.getRole()));
        System.out.println(Arrays.toString(role));
        userService.updateUser(user, id);
        return "redirect:/";
    }
}
