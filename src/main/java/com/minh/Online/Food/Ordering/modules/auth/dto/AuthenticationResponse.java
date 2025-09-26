package com.minh.Online.Food.Ordering.modules.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minh.Online.Food.Ordering.modules.user.USER_ROLE;
import lombok.Getter;

@Getter
public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("message")
    private String message;
    
    @JsonProperty("role")
    private USER_ROLE role;

    public AuthenticationResponse(String accessToken, String refreshToken, String message, USER_ROLE role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getMessage() {
        return message;
    }
    
    public USER_ROLE getRole() {
        return role;
    }
}
