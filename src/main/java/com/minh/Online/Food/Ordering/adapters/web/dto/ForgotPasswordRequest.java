package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ForgotPasswordRequest {
    @Email @NotBlank
    private String email;
}

