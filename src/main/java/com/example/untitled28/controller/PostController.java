package com.example.untitled28.controller;

import com.example.untitled28.dto.CommentDto;
import com.example.untitled28.dto.PostDto;
import com.example.untitled28.model.Comment;
import com.example.untitled28.model.Post;
import com.example.untitled28.model.User;
import com.example.untitled28.repository.CommentRepository;
import com.example.untitled28.repository.PostRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private User currentUser(HttpSession httpSession) {
        return (User) httpSession.getAttribute("user"); // 유저가 들어왔는지 확인하기 위한것
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postRepository.findAll());

        return "post-list";
    }

    @GetMapping("/add")
    public String addForm(Model model, HttpSession httpSession) {
        if (currentUser(httpSession) == null) return "redirect:/login"; // 유저가 안들어왔으면 로그인페이지로

        model.addAttribute("postDto", new PostDto());

        return "post-form";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute PostDto postDto,
                      BindingResult bindingResult,
                      HttpSession httpSession) {
        if (bindingResult.hasErrors()) return "post-form";

        User user = currentUser(httpSession);
        Post post = Post.builder()
                        .title(postDto.getTitle())
                        .content(postDto.getContent())
                        .author(user)
                        .createdAt(LocalDateTime.now())
                        .build();

        postRepository.save(post);

        return "redirect:/posts";
    }

    @GetMapping("/{id}")                                // 동적경로 : /{~}
    public String detail(@PathVariable Integer id,      // @PathVariable : 주소에 대한 동적 값
                         Model model,
                         HttpSession httpSession) {
        Post post = postRepository.findById(id).orElseThrow();

        model.addAttribute("post", post);
        model.addAttribute("commentDto", new CommentDto());

        return "post-detail";
    }

    @PostMapping("/{postId}/comments")
    public String addComment(@PathVariable Integer postId,    // @PathVariable : 주소에 대한 동적 값
                             @Valid @ModelAttribute CommentDto commentDto,
                             BindingResult bindingResult,
                             HttpSession httpSession,         // 댓글도 누군지 확인하고 작성하게 해야하니 세션이 필요하다
                             Model model) {
        Post post = postRepository.findById(postId).orElseThrow();

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);

            return "post-detail";
        }
        User user = currentUser(httpSession);           // 세션에서 유저 정보를 꺼내오는것
        Comment comment = Comment.builder()
                                 .post(post)
                                 .author(user)          // author : 유저정보
                                 .text(commentDto.getText())
                                 .createdAt(LocalDateTime.now())
                                 .build();
        commentRepository.save(comment);

        return "redirect:/posts/" + postId;
    }

}