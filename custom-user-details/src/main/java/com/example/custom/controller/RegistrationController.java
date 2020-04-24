package com.example.custom.controller;


import com.example.domain.dto.UserDto;
import com.example.domain.service.UserRegistrationServiceNoSql;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRegistrationServiceNoSql service;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user",new UserDto());
        return "registration";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDto user, BindingResult result) {
        if(result.hasErrors()) {
            return "registration";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.createUser(user);
        return "redirect:register?success";
    }

}
