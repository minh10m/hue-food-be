package com.minh.Online.Food.Ordering.adapters.web.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemRequest {

    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
    private int quantity;
}

