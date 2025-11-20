package com.minh.Online.Food.Ordering.adapters.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;

    public AuthenticationResponse() {}
    public AuthenticationResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken; this.refreshToken = refreshToken;
    }

}
