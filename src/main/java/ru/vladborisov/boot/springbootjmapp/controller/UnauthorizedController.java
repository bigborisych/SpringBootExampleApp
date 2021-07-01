package ru.vladborisov.boot.springbootjmapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vladborisov.boot.springbootjmapp.model.User;

@Controller
@RequestMapping("/")
public class UnauthorizedController {

    @GetMapping
    public String getDefaultPage() {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
}
