package com.example.untitled28.controller;

import com.example.untitled28.dto.SignupDto;
import com.example.untitled28.model.User;
import com.example.untitled28.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SignupController {
    private final UserRepository userRepository;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupDto", new SignupDto());

        return "signup";
    }

    // 에러가 발생하면 다시 회원가입으로 보낸다
    // .isPresent() : 존재하는지 안하는지에 따라 ture, false 값을 내보낸다

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignupDto signupDto,
                         BindingResult bindingResult,
                         Model model) {
        if(bindingResult.hasErrors()) return "signup";
                                                              // .isPresent() : 존재여부에 따라 ture, false 값을 내보낸다
        if(userRepository.findByUsername(signupDto.getUsername()).isPresent()) {
            model.addAttribute("error", "이미 사용중인 아이디입니다");

            return "signup"; // 에러가 발생하면 다시 회원가입으로 보낸다
        }

        userRepository.save(User.builder()
                .username(signupDto.getUsername())
                .password(signupDto.getPassword())
                .build());

        return "redirect:/login?registered"; // 로그인페이지로 리다이렉트시킬건데 레지스터드라는 값을 보내가지고
    }                                        // 로그인페이지로 들어온유저가 회원가입으로 들어왔는지 주소값을 입력해서 들어왔는지 확인하는것
}