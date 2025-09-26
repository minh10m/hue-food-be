package com.minh.Online.Food.Ordering.modules.auth.dto;

import com.minh.Online.Food.Ordering.modules.user.USER_ROLE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotBlank
    private String fullName;

    private USER_ROLE role;
}

