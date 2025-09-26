package com.minh.Online.Food.Ordering.modules.user.dto;

import com.minh.Online.Food.Ordering.modules.user.User;
import lombok.Data;

@Data
public class UserProfileDTO {
    private String fullName;
    private String email;
    private String role;

    public static UserProfileDTO fromUser(User user) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        return dto;
    }
}
