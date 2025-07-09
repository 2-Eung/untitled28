package com.example.untitled28.controller;

import com.example.untitled28.dto.LoginDto;
import com.example.untitled28.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserRepository userRepository;

    @GetMapping({"/", "/login"})
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());

        return "login";
    }
}
