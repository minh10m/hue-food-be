package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserProfileDTO {
    // getters/setters
    private Long id;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String role;
    private boolean enabled;

}
