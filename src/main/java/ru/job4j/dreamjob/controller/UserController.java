package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService service;
    private static final String SUCCESS_MESSAGE = "Регистрация прошла успешно.";

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/formRegistration")
    public String createUser(Model model) {
        model.addAttribute("user", new User(0, "email", "password"));
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, Model model) {
        Optional<User> regUser = service.add(user);
        String result = "redirect:/result";
        if (regUser.isEmpty()) {
            model.addAttribute("error", true);
            result = "registration";
        }
        return result;
    }

    @GetMapping("/result")
    public String registrationResult(Model model) {
        model.addAttribute("message", SUCCESS_MESSAGE);
        return "result";
    }
}
