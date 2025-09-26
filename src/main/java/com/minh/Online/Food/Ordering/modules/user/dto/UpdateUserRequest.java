package com.minh.Online.Food.Ordering.modules.user.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String fullName;
    private String email;
    // Note: Password updates should be handled separately for security
}
