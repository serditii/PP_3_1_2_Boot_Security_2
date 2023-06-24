package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    private final UserValidator userValidator;

    public UserController(UserService userServise, UserValidator userValidator) {
        this.userService = userServise;
        this.userValidator = userValidator;
    }

    @GetMapping("/admin")
    public String adminPage(ModelMap model) {
        return "admin";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/logout";
    }

    @GetMapping("/user")
    public String userPage(ModelMap model, Principal principal) {
        model.addAttribute("user", userService.
                showUser(userService.findByUsername(principal.getName()).getId()));
        return "user";
    }

    @GetMapping
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);
        return "index";
    }

    @GetMapping("/users")
    public String showAll(ModelMap model) {
        List<User> list = userService.getListUsers();
        model.addAttribute("users", list);
        return "users";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute() @Valid User user,
                         BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "new";
        }
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/new")
    public String newUser(@ModelAttribute() User user) {
        return "new";
    }

    @GetMapping("/users/{id}")
    public String show(@PathVariable() long id, ModelMap model) {
        model.addAttribute("user", userService.showUser(id));
        return "show";
    }

    @GetMapping("/users/{id}/edit")
    public String edit(@PathVariable() long id, ModelMap model) {
        model.addAttribute("user", userService.showUser(id));
        return "edit";
    }

    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute() @Valid User user,
                         BindingResult bindingResult, ModelMap model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.updateUser(user);
        return "redirect:/users";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable() long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
