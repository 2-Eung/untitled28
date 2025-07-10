package com.example.untitled28.controller;

import com.example.untitled28.dto.LoginDto;
import com.example.untitled28.model.User;
import com.example.untitled28.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserRepository userRepository;

    @GetMapping({"/", "/login"})
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());

        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDto loginDto,
                        BindingResult bindingResult,
                        HttpSession httpSession,
                        Model model) {
        if (bindingResult.hasErrors()) return "login";

        User user = userRepository.findByUsername(loginDto.getUsername()).orElse(null);

        if (user == null || !user.getPassword().equals(loginDto.getPassword())) { // 아이디나 비밀번호를 잘못입력한 경우
            model.addAttribute("error", "아이디/비밀번호가 올바르지 않습니다");

            return "login";
        }
        httpSession.setAttribute("user", user); // 세션 부여

        return "redirect:/posts";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/login";
    }
}
