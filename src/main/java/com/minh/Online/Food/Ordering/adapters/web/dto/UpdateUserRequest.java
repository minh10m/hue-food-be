package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRequest {
    // getters/setters
    @Size(max = 100, message = "Tên tối đa 100 ký tự")
    private String fullName;

    @Size(max = 500, message = "URL avatar tối đa 500 ký tự")
    private String avatarUrl;

}

