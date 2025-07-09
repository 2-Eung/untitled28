package com.example.untitled28.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    @NotBlank
    @Size(min=3, max=50)            // DTO 선에서의 조건
    private String username;

    @NotBlank
    @Size(min=6, max=100)
    private String password;
}
