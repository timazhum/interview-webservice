package com.timazhum.interview.controllers;

import com.timazhum.interview.models.User;
import com.timazhum.interview.viewmodels.UserRegistrationDto;
import com.timazhum.interview.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping("/register")
    public String showUserRegistrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public String tryRegisterUser(@ModelAttribute("user") @Valid UserRegistrationDto userRegistrationDto,
                                  BindingResult result) {
        Optional<User> optionalUser = userService.tryRegisterUser(userRegistrationDto, result);

        if (optionalUser.isPresent()) {
            return "redirect:/login?register=true";
        } else {
            return "register";
        }
    }

}
