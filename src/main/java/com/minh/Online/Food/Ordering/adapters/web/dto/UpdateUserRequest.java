package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.Size;

public class UpdateUserRequest {
    @Size(max = 100, message = "Tên tối đa 100 ký tự")
    private String fullName;

    @Size(max = 500, message = "URL avatar tối đa 500 ký tự")
    private String avatarUrl;

    // getters/setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}

